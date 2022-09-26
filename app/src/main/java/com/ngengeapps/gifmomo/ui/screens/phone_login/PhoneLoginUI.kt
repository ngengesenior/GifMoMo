package com.ngengeapps.gifmomo.ui.screens.phone_login

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ngengeapps.gifmomo.MAIN_SCREEN
import com.ngengeapps.gifmomo.R
import com.ngengeapps.gifmomo.common.CameroonNumberVisualTransformation
import com.ngengeapps.gifmomo.model.Response
import com.ngengeapps.gifmomo.ui.screens.sign_up.AuthViewModel


//@Preview(showSystemUi = true, showBackground = true)
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun PhoneLoginUI(
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavController,
    destination:String,
    restartLogin: () -> Unit
) {
    val context = LocalContext.current
    // Sign up state
    val uiState by viewModel.signUpUiState
        .collectAsState(initial = Response.NotInitialized)

    // SMS code
    val code by viewModel.code.collectAsState(initial = "")

    // Phone number
    val phone by viewModel.number.collectAsState(initial = "")

    val focusManager = LocalFocusManager.current

    when (uiState) {
        // Nothing happening yet
        is Response.NotInitialized -> {
            
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 56.dp, horizontal = 10.dp)) {
                Text(text = stringResource(R.string.verify_phone),
                    style = MaterialTheme.typography.h6)

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = stringResource(id = R.string.gif_momo_sign_up_message))

                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically){
                    Text(text = stringResource(id = R.string.cameroon_code))
                    Spacer(modifier = Modifier.width(4.dp))
                    OutlinedTextField(
                        value = phone,
                        onValueChange = viewModel::onNumberChange,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        textStyle = LocalTextStyle.current.copy(fontWeight = FontWeight.ExtraBold),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                            viewModel.authenticatePhone(phone)
                        }),
                        visualTransformation = CameroonNumberVisualTransformation(),
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp)
                    )

                }
                
            }
        }

        // State loading
        is Response.Loading -> {
            val text = (uiState as Response.Loading).message
            if (text == context.getString(R.string.code_sent)) {

                // If the code is sent, display the screen for code
                EnterCodeUI(
                    code = code,
                    onCodeChange = viewModel::onCodeChange,
                    phone = phone,
                    onGo = {
                        Log.d("Code Sent", "The code is $code")
                        focusManager.clearFocus()
                        viewModel.verifyOtp(code)
                    })

            } else {

                // If the loading state is different form the code sent state,
                // show a progress indicator
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    text?.let {
                        Text(
                            it, modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }

                }


            }

        }

        // If it is the error state, show the error UI
        is Response.Error -> {
            val throwable = (uiState as Response.Error).exception!!
            ErrorUi(exc = throwable, onRestart = restartLogin)
        }

        // You can navigate when the auth process is successful
        is Response.Success -> {
            Log.d("Code", "The Sign in was successful")
            navController.navigate(destination)
        }

    }


}