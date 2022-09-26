package com.ngengeapps.gifmomo.model.service

import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.ngengeapps.gifmomo.model.Response
import kotlinx.coroutines.flow.MutableStateFlow

interface AccountService {
    val signUpState: MutableStateFlow<Response>
    fun hasUser(): Boolean
    fun getUserId(): String
    fun createAccount(name: String, email: String)
    fun authenticate(phone: String)
    fun signOut()
    fun onCodeSent(
        verificationId: String,
        token: PhoneAuthProvider.ForceResendingToken
    )

    fun onVerifyOtp(code: String)
    fun createAnonymousAccount()
    fun onVerificationCompleted(credential: PhoneAuthCredential)
    fun onVerificationFailed(exception: Exception)
    fun getUserPhone(): String

}