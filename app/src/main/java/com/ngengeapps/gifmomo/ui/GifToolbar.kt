package com.ngengeapps.gifmomo.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GifToolbar(
    title: String,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h6
            )
        },
        backgroundColor = MaterialTheme.colors.surface,
        modifier = modifier
    )
}