package com.ngengeapps.gifmomo.ui.screens.phone_login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.ngengeapps.gifmomo.R
import com.ngengeapps.gifmomo.databinding.ActivityPhoneSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhoneSignInActivity : AppCompatActivity() {
    private lateinit var binding:ActivityPhoneSignInBinding
    private val phoneBuilder = AuthUI.IdpConfig.PhoneBuilder()
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneSignInBinding.inflate(layoutInflater)
        binding.phoneField.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when(actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    setDefaultNumber()
                    createSignInIntent()
                    true
                }
                else -> {
                    true
                }
            }
        }
        binding.buttonNext.setOnClickListener{
            setDefaultNumber()
            createSignInIntent()
        }
        setContentView(binding.root)
    }

    fun setDefaultNumber() {
        phoneBuilder.setDefaultNumber("+237${binding.phoneField.text.toString()}")
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {

            val user = FirebaseAuth.getInstance().currentUser
            Log.d("User", "onSignInResult:Successful $user")
            // ...
        } else {
           Toast.makeText(this,"Phone auth failed",Toast.LENGTH_LONG)
               .show()
        }
    }

    private fun createSignInIntent() {
        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(arrayListOf(phoneBuilder.build()))
            .build()
        signInLauncher.launch(intent)

    }


}