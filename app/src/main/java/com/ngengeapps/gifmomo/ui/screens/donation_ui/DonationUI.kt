package com.ngengeapps.gifmomo.ui.screens.donation_ui

import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ngengeapps.gifmomo.common.ui.MainGifMoMoButton
import com.ngengeapps.gifmomo.common.ui.SecondaryGifMoMoButton
import com.ngengeapps.gifmomo.ui.GifToolbar
import net.compay.android.models.requests.CollectionRequest
import java.util.UUID


@ExperimentalComposeUiApi
@Composable
fun DonationUI(viewModel: DonationViewModel = hiltViewModel()) {
    val donationAmount: String by viewModel.donationAmount.collectAsState(initial = "")
    val focusManager = LocalFocusManager.current
    val showDialog by viewModel.showDialog.collectAsState(initial = false)

    Scaffold(topBar = {
        GifToolbar(title = "How much are you donating?")
    }) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = donationAmount,
                onValueChange = viewModel::onAmountChange, singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Go
                ),
                textStyle = LocalTextStyle.current.copy(fontWeight = FontWeight.ExtraBold),

                keyboardActions = KeyboardActions(onGo = {
                    focusManager.clearFocus()
                }),
                trailingIcon = {
                    Icon(Icons.Default.Check, "",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.clickable {
                            focusManager.clearFocus()
                        })
                },
                leadingIcon = {
                    Text(
                        text = "XAF",
                        fontWeight = FontWeight.Bold
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )

            Text(
                text = if (donationAmount.isEmpty()) "0"
                else "XAF ${String.format("%,d", donationAmount.toInt())}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(24.dp))

            MainGifMoMoButton(
                onClick = {
                          val request = CollectionRequest.CollectionRequestBuilder.aCollectionRequest()
                              .withAmount("100")
                              .withCurrency("XAF")
                              .withFrom(Firebase.auth.currentUser?.phoneNumber)
                              .withExternalReference(UUID.randomUUID().toString())
                              .withDescription("Sample description")
                              .build()
                    viewModel.makePayment(request)

                }, text = "Donate",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))
            SecondaryGifMoMoButton(
                onClick = {

                          ActivityResultContracts.GetContent()
                }, text = "Share",
                modifier = Modifier.fillMaxWidth()
            )

            if (showDialog) {
                Dialog(onDismissRequest = {  }, properties = DialogProperties(usePlatformDefaultWidth = false)) {
                    Box(modifier = Modifier.size(200.dp), contentAlignment = Alignment.Center){
                        Column {
                            CircularProgressIndicator()
                            Text(text = "Confirm payment when prompter or dial *126#")
                        }
                    }

                }
            }
        }

    }

}