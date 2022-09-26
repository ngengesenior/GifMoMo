package com.ngengeapps.gifmomo.ui.screens.sign_up

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngengeapps.gifmomo.model.Response
import com.ngengeapps.gifmomo.model.User
import com.ngengeapps.gifmomo.model.repositories.UserRepository
import com.ngengeapps.gifmomo.model.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@HiltViewModel
class AuthViewModel @Inject constructor(private val accountService: AccountService,
                                        private val userRepository: UserRepository) : ViewModel() {
    private val _signUpState: MutableStateFlow<Response> = accountService.signUpState
    val signUpUiState: StateFlow<Response> get() = _signUpState

    private val _number: MutableStateFlow<String> = MutableStateFlow("")
    val number: StateFlow<String> get() = _number


    private val _name: MutableStateFlow<String> = MutableStateFlow("")
    val name: StateFlow<String> get() = _name

    private val _email: MutableStateFlow<String> = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _code: MutableStateFlow<String> = MutableStateFlow("")
    val code: StateFlow<String> get() = _code

    private val _userCreateState:MutableStateFlow<Response> = MutableStateFlow(Response.NotInitialized)
    val userCreatedState:StateFlow<Response> get()= _userCreateState


    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onNameChange(name: String) {
        _name.value = name
    }

    fun authenticatePhone(phone: String) {
        val prefix = if (!phone.startsWith("+237")) "+237" else ""
        accountService.authenticate("$prefix$phone")
    }

    fun onNumberChange(number: String) {
        _number.value = number.take(9)
    }

    fun onCodeChange(code: String) {
        _code.value = code.take(6)
    }

    fun verifyOtp(code: String) {
        viewModelScope.launch {
            accountService.onVerifyOtp(code)
        }
    }

    fun createUser(user: User) {
        _userCreateState.value = Response.Loading("Creating user account")
        userRepository.createUser(user)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    _userCreateState.value = Response.Success("Account creation complete")
                } else {
                    val ex = it.exception
                    _userCreateState.value = Response.Error(ex)
                }
            }
    }

    fun createUserFromCurrentFirebaseUser() {
        userRepository.createUserFromCurrent()
    }


}