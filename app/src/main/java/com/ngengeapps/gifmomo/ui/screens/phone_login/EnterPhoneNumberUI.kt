package com.ngengeapps.gifmomo.ui.screens.phone_login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ngengeapps.gifmomo.R
import com.ngengeapps.gifmomo.common.CameroonNumberVisualTransformation
import com.ngengeapps.gifmomo.common.ui.PhoneNumberTextField

@Composable
fun EnterPhoneNumberUI(
    modifier: Modifier = Modifier
        .padding(vertical = 56.dp, horizontal = 24.dp),
    onClick: () -> Unit,
    phone: String,
    onPhoneChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.verify_phone),
            style = MaterialTheme.typography.h6
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.gif_momo_sign_up_message))

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(value = phone,
            onValueChange = onPhoneChange ,
            singleLine = true,
            leadingIcon = {
                Text(text = stringResource(R.string.cameroon_code) )
            }
        )
        Button(
            onClick,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = stringResource(id = R.string.next))
        }

    }
}