package com.ngengeapps.gifmomo.ui.screens.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.lifecycleScope
import com.ngengeapps.gifmomo.LOGIN_SCREEN
import com.ngengeapps.gifmomo.MAIN_SCREEN
import com.ngengeapps.gifmomo.ui.screens.main.HomeActivity
import com.ngengeapps.gifmomo.ui.screens.phone_login.PhoneSignInActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    private val viewModel:SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreenUI()
        }
        observeState()
    }


    private fun observeState() {
        lifecycleScope.launch {
            viewModel.destination.collect {
                if(it.isNotEmpty()){
                    if (it == MAIN_SCREEN) {
                        startActivity(Intent(this@SplashActivity,HomeActivity::class.java))
                    } else if(it == LOGIN_SCREEN) {
                        startActivity(Intent(this@SplashActivity,PhoneSignInActivity::class.java))
                    }
                    finish()
                }
            }
        }
    }
}