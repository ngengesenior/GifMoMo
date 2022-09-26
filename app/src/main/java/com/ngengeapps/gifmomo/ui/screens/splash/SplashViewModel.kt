package com.ngengeapps.gifmomo.ui.screens.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ngengeapps.gifmomo.LOGIN_SCREEN
import com.ngengeapps.gifmomo.MAIN_SCREEN
import com.ngengeapps.gifmomo.SIGN_UP_SCREEN
import com.ngengeapps.gifmomo.SPLASH_SCREEN
import com.ngengeapps.gifmomo.model.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService,
) : ViewModel() {



   // private val currentUser = firebase.auth.currentUser
    private val _destination:MutableStateFlow<String> = MutableStateFlow("")
    val destination:StateFlow<String> get() = _destination


    init {
        onAppStart()
    }
    private fun onAppStart() {
        viewModelScope.launch {
            delay(3000L)
            if (accountService.hasUser()) _destination.value = MAIN_SCREEN  else {
                _destination.value = LOGIN_SCREEN
            }
        }

    }
}