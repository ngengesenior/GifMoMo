package com.ngengeapps.gifmomo.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ngengeapps.gifmomo.R

@Composable
//@Preview(showBackground = true, showSystemUi = true)
fun SplashScreenUI() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        /*val destination by viewModel.destination.collectAsState(initial = "")

        if (destination.isNotEmpty()) {
            onNavigateTo(destination)
        }*/

        Text(
            stringResource(R.string.app_name),
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.h4
        )
        Spacer(modifier = Modifier.height(8.dp))
        Surface(color = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(50.dp)
        ) {
            Image(painter = painterResource(R.mipmap.ic_launcher),
                contentDescription = null, modifier = Modifier.size(80.dp) )
        }

    }
}