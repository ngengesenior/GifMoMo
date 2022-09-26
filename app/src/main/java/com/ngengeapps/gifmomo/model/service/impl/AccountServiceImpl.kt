package com.ngengeapps.gifmomo.model.service.impl

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngengeapps.gifmomo.MainActivity
import com.ngengeapps.gifmomo.R
import com.ngengeapps.gifmomo.common.snackbar.SnackbarManager
import com.ngengeapps.gifmomo.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.ngengeapps.gifmomo.model.Response
import com.ngengeapps.gifmomo.model.User
import com.ngengeapps.gifmomo.model.service.AccountService
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import com.ngengeapps.gifmomo.R.string as AppText


@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
class AccountServiceImpl @Inject constructor(
    private val snackbarManager: SnackbarManager,
    private val context: MainActivity
) : AccountService
{
    private val TAG = AccountService::class.java.simpleName
    private val firebase = Firebase
    private val auth = firebase.auth

    var verificationOtp: String = ""
    var resentToken: PhoneAuthProvider.ForceResendingToken? = null
    override var signUpState: MutableStateFlow<Response> =
        MutableStateFlow(Response.NotInitialized)
        private set


    private val authCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.i(
                TAG,
                "onVerificationCompleted: Verification completed. ${context.getString(AppText.verification_complete)}"
            )
            signUpState.value =
                Response.Loading(message = context.getString(AppText.verification_complete))
            snackbarManager.showMessage(AppText.verification_complete)
            signInWithAuthCredential(credential)
        }

        override fun onVerificationFailed(exception: FirebaseException) {
            when (exception) {
                is FirebaseAuthInvalidCredentialsException -> {
                    signUpState.value =
                        Response.Error(exception = Exception(context.getString(AppText.verification_failed_try_again)))
                    snackbarManager.showMessage(AppText.verification_failed_try_again)
                }
                is FirebaseTooManyRequestsException -> {
                    signUpState.value =
                        Response.Error(exception = Exception(context.getString(AppText.quota_exceeded)))
                    snackbarManager.showMessage(AppText.quota_exceeded)
                }
                else -> {
                    signUpState.value = Response.Error(exception)
                    snackbarManager.showMessage(exception.toSnackbarMessage())
                }
            }

        }

        override fun onCodeSent(code: String, token: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(code, token)
            verificationOtp = code
            resentToken = token
            signUpState.value = Response.Loading(message = context.getString(AppText.code_sent))
        }

    }

    private val authBuilder: PhoneAuthOptions.Builder = PhoneAuthOptions.newBuilder(auth)
        .setCallbacks(authCallbacks)
        .setActivity(context)
        .setTimeout(120L, TimeUnit.SECONDS)

    private fun signInWithAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i(TAG, "signInWithAuthCredential:The sign in succeeded ")
                    signUpState.value =
                        Response.Success(message = context.getString(R.string.phone_auth_success))
                } else {


                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Log.e(TAG, context.getString(R.string.invalid_code))
                        signUpState.value =
                            Response.Error(exception = Exception(context.getString(R.string.invalid_code)))
                        snackbarManager.showMessage(AppText.invalid_code)
                        return@addOnCompleteListener
                    } else {
                        signUpState.value = Response.Error(task.exception)
                        Log.e(TAG, "signInWithAuthCredential: Error ${task.exception?.message}")
                        snackbarManager.showMessage(task.exception!!.toSnackbarMessage())
                    }
                }

            }

    }

    override fun hasUser(): Boolean {
        return auth.currentUser != null
    }

    override fun getUserId(): String {
        return auth.currentUser?.uid.orEmpty()
    }

    override fun createAccount(name: String, email: String) {
        signUpState.value = Response.Loading(context.getString(AppText.creating_account))
        firebase.firestore
            .collection("users")
            .document(getUserId())
            .set(
                User(name = name,
                email, phone = getUserPhone(), id = getUserId())
            )
            .addOnSuccessListener {
                snackbarManager.showMessage(AppText.account_creation_success)
                signUpState.value =
                    Response.Success(context.getString(AppText.account_creation_success))
                return@addOnSuccessListener
            }.addOnFailureListener {
                snackbarManager.showMessage(it.toSnackbarMessage())
                signUpState.value = Response.Error(it)
                return@addOnFailureListener
            }
    }

    /*fun setActivity(activity: Activity){
        authbuilder.setActivity(activity)
    }*/

    override fun authenticate(phone: String) {
        signUpState.value =
            Response.Loading("${context.getString(R.string.code_will_be_sent)} $phone")
        val options = authBuilder
            .setPhoneNumber(phone)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    override fun signOut() {
        auth.signOut()
    }

    override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
        authCallbacks.onCodeSent(verificationId, token)
    }

    override fun onVerifyOtp(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationOtp, code)
        signInWithAuthCredential(credential)
    }

    override fun createAnonymousAccount() {
        auth.signInAnonymously()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    signUpState.value =
                        Response.Success(context.getString(AppText.anonymous_account_create_success))
                } else {
                    if (it.exception != null) {
                        signUpState.value = Response.Error(it.exception)
                    }
                }
            }
    }

    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
        authCallbacks.onVerificationCompleted(credential)
    }

    override fun onVerificationFailed(exception: Exception) {
        authCallbacks.onVerificationFailed(exception as FirebaseException)
    }

    override fun getUserPhone(): String {
        return auth.currentUser?.phoneNumber.orEmpty()
    }
}