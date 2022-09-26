package com.ngengeapps.gifmomo.ui.screens.sign_up

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ngengeapps.gifmomo.R
import com.ngengeapps.gifmomo.common.ui.MainGifMoMoButton
import com.ngengeapps.gifmomo.model.Response
import com.ngengeapps.gifmomo.R.string as AppText

//@Preview(showBackground = true, showSystemUi = true)
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun SignUpUI(popUpScreen: () -> Unit, viewModel: AuthViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val authState by viewModel.signUpUiState.collectAsState(initial = Response.NotInitialized)
    val name by viewModel.name.collectAsState(initial = "")
    val email by viewModel.email.collectAsState(initial = "")

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (authState) {

            is Response.NotInitialized -> {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.h6
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(value = name, onValueChange = viewModel::onNameChange,
                    singleLine = true,
                    label = { Text(text = stringResource(R.string.name)) },
                    leadingIcon = {
                        Icon(Icons.Default.AccountCircle, contentDescription = "")
                    })

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(value = email, onValueChange = viewModel::onEmailChange,
                    label = { Text(text = stringResource(R.string.email)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = "")
                    })
                Spacer(modifier = Modifier.height(20.dp))

                MainGifMoMoButton(onClick = {

                }, text = stringResource(id = R.string.create_account))

            }

            is Response.Loading -> {
                val message = (authState as Response.Loading).message
                if (message == context.getString(AppText.creating_account)) {

                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = message)
                }
            }

            is Response.Success -> {
                popUpScreen()
            }

            is Response.Error -> {
                val message = (authState as Response.Error).exception
            }
        }
    }

}