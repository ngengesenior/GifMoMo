package com.ngengeapps.gifmomo.ui.screens.phone_login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ngengeapps.gifmomo.R
import com.ngengeapps.gifmomo.common.CameroonNumberVisualTransformation
import com.ngengeapps.gifmomo.common.PinVisualTransformation

@Composable
fun EnterCodeUI(
    code: String, onCodeChange: (String) -> Unit,
    phone: String, onGo: (KeyboardActionScope.() -> Unit)?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = 56.dp,
                horizontal = 20.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "(+237) ${CameroonNumberVisualTransformation.doFiltering(AnnotatedString(phone)).text.text}",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Waiting to automatically detect an SMS sent to (+237) ${
                CameroonNumberVisualTransformation.doFiltering(
                    AnnotatedString(phone)
                ).text.text
            }"
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = code, onValueChange = onCodeChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Go
            ),
            keyboardActions = KeyboardActions(onGo = onGo),
            singleLine = true,
            label = { Text(text = stringResource(R.string.code)) },
            visualTransformation = PinVisualTransformation(),
            modifier = Modifier.fillMaxWidth(0.45f)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(R.string.enter_digits_code))
    }

}