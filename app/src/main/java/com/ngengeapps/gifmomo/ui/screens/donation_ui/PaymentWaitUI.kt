package com.ngengeapps.gifmomo.ui.screens.donation_ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ngengeapps.gifmomo.R
import com.ngengeapps.gifmomo.common.ui.SecondaryGifMoMoButton


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PaymentWaitUI (

){
    Column(verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.padding(horizontal = 24.dp)) {
        Text(text = stringResource(R.string.confirm_payment_prompt))
        Spacer(modifier = Modifier.height(15.dp))
        CircularProgressIndicator(strokeWidth = 6.dp, progress = 1f,
        modifier = Modifier.widthIn(60.dp,80.dp))
        Spacer(modifier = Modifier.height(15.dp))
        SecondaryGifMoMoButton(onClick = {  },
        text = "Dial *126#")

    }
}