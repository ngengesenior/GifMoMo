package com.ngengeapps.gifmomo.common.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ngengeapps.gifmomo.R
import com.ngengeapps.gifmomo.common.CameroonNumberVisualTransformation


@Composable
fun MainGifMoMoButton(
    onClick: () -> Unit, text: String = "",
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .height(45.dp)
            .widthIn(200.dp, max = 450.dp),
        colors = ButtonDefaults.buttonColors( contentColor = colors.secondary)

    ) {
        Text(text = text)
    }
}


@Composable
fun SecondaryGifMoMoButton(onClick: () -> Unit, text: String = "",
                           modifier: Modifier = Modifier,) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .height(45.dp)
            .widthIn(200.dp, max = 450.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor =
        colors.secondary, contentColor = MaterialTheme.colors.onSecondary)

    ) {
        Text(text = text)
    }

}


@Composable
fun PhoneNumberTextField(
    value: String,
    onNumberChange: (String) -> Unit,
    onDone: (KeyboardActionScope.() -> Unit)?
) {
    OutlinedTextField(
        value = value, onValueChange = onNumberChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = onDone),
        singleLine = true,
        visualTransformation = CameroonNumberVisualTransformation(),
        label = {
            Text(
                text = stringResource(R.string.cameroon_code),
                style = MaterialTheme.typography.subtitle1
            )
        },
        leadingIcon = {
            Icon(Icons.Default.Phone, contentDescription = "")
        },

        )
}