package com.ngengeapps.gifmomo.ui.screens.create_campain

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.InsertPhoto
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.LocationSearching
import androidx.compose.material.icons.rounded.Money
import androidx.compose.material.icons.rounded.Title
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.google.android.libraries.places.api.model.Place
import com.ngengeapps.gifmomo.R
import com.ngengeapps.gifmomo.common.MapLauncherContract
import com.ngengeapps.gifmomo.common.ui.MainGifMoMoButton
import com.ngengeapps.gifmomo.model.Campaign
import com.ngengeapps.gifmomo.model.Response
import com.ngengeapps.gifmomo.ui.GifToolbar
import java.util.*


@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun CreateCampaignUi(
    viewModel: CampaignViewModel,
    openCategoryRoute: () -> Unit,
    onCreateSuccess:()->Unit
) {
    Scaffold(topBar = {
        GifToolbar(title = "Create GifMoMo Campaign")
    }) {

        val status by viewModel.createCampaignResponse.collectAsState(initial = Response.NotInitialized)

        if (status is Response.Success){
            onCreateSuccess()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 10.dp, end = 10.dp,
                    bottom = 50.dp
                )
                .verticalScroll(rememberScrollState())
        ) {
            val selectedCategory by viewModel.selectedCategoryIndex.collectAsState(initial = -1 to "")
            val targetAmount by viewModel.amount.collectAsState(initial = "")
            val title by viewModel.title.collectAsState(initial = "")
            val description by viewModel.description.collectAsState(initial = "")
            val context = LocalContext.current

            val focusManager = LocalFocusManager.current
            var showDialog: Boolean by rememberSaveable { mutableStateOf(false) }

            val place: Place? by viewModel.place.collectAsState(initial = null)

            val calendar = Calendar.getInstance(TimeZone.getDefault())
            calendar.time = Date()

            val userId: String by viewModel.currentUser.collectAsState(initial = "")

            val endDate by viewModel.endDate.collectAsState(
                initial = ""
            )

            val endDatePicker = DatePickerDialog(
                context,
                { _: DatePicker, year: Int, month: Int, day: Int ->
                    viewModel.updateEndDate("$day/${month+1}/$year")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            val placeLauncher =
                rememberLauncherForActivityResult(contract = MapLauncherContract()) { newPlace ->

                    newPlace?.let {
                        viewModel.onChangePlace(it)
                    }

                }

            val pictureLauncher =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { newUri ->
                    newUri?.let {
                        viewModel.selectImageFromGallery(it)
                    }
                }

            val imageUri by viewModel.imageUri.collectAsState(initial = null)

            val showDialogCreateDialog:Boolean by viewModel.showCreateDialog.collectAsState(initial = false)

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = title, onValueChange = viewModel::onTitleChanged,
                leadingIcon = {
                    Icon(Icons.Rounded.Title, null)
                },
                label = {
                    Text(text = "Fundraiser title")
                }, modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                )
            )

            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = description, onValueChange = viewModel::onDescriptionChanged, label = {
                    Text(text = "Describe the purpose of the fund")
                }, singleLine = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 150.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = targetAmount,
                onValueChange = viewModel::onAmountChanged,
                label = {
                    Text(text = "What is the target amount?")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.clearFocus()
                }),
                leadingIcon = {
                    Icon(Icons.Rounded.Money, null)
                },
                trailingIcon = {
                    Text(text = "XAF", color = MaterialTheme.colors.primary)
                },
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = if (targetAmount.isEmpty()) "0"
                else "XAF ${String.format("%,d", targetAmount.toInt())}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(value = selectedCategory.second, onValueChange = {},
                leadingIcon = {
                    Icon(Icons.Rounded.Category, null)
                },
                label = {
                    Text(text = "Category")
                },
                textStyle = MaterialTheme.typography.subtitle1,
                singleLine = true,
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        showDialog = true
                    }
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(value = place?.name ?: "", onValueChange = {},
                leadingIcon = {
                    Icon(Icons.Rounded.LocationSearching, contentDescription = "")
                },
                label = {
                    Text(text = "Where is it taking place?")
                },
                textStyle = MaterialTheme.typography.subtitle1,
                singleLine = true,
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        placeLauncher.launch(context)
                    }
            )

            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(value = endDate, onValueChange = {},

                textStyle = MaterialTheme.typography.subtitle1,
                label = {
                    Text(text = stringResource(R.string.end_date))
                },
                singleLine = true,
                enabled = false,

                modifier = Modifier
                    .weight(0.35f)
                    .clickable {
                        endDatePicker.show()
                    }
            )


            Spacer(modifier = Modifier.height(24.dp))

            if (imageUri == null) {
                Icon(Icons.Default.InsertPhoto, contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            pictureLauncher.launch("image/*")
                        }
                        .size(60.dp)
                )
            }

            imageUri?.let {
                AndroidView(factory = { context ->

                    val imageView = ImageView(context).apply {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }
                    imageView.setImageURI(imageUri)

                    imageView

                }, modifier = Modifier
                    .size(60.dp)
                    .clickable {
                        pictureLauncher.launch("image/*")
                    }
                    .align(Alignment.CenterHorizontally)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))


            MainGifMoMoButton(text = "Create Campaign", onClick = {

                val campaign = Campaign(
                    title = title,
                    description = description,
                    latitude = place?.latLng?.latitude ?: 0.0,
                    longitude = place?.latLng?.longitude ?: 0.0,
                    endDate = endDate,
                    targetAmount = targetAmount.toLong(),
                    placeName = place?.name.orEmpty(),
                    category = selectedCategory.second
                )
                viewModel.createCampaign(campaign)
            }, modifier = Modifier.fillMaxWidth())

            if (showDialogCreateDialog) {
                Dialog(
                    onDismissRequest = {

                    },
                    content = {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(200.dp),
                        ){
                            Column {
                                CircularProgressIndicator()
                                Text(text = "Creating Campaign")
                            }
                        }
                    },
                    properties = DialogProperties(
                        dismissOnClickOutside = false,
                    ),
                )
            }
            if (showDialog) {
                Dialog(
                    onDismissRequest = {
                                       showDialog = false
                    },
                    content = {
                        SelectCategoryUi(viewModel = viewModel, closeDialog = {
                            showDialog = false
                        })
                    },
                    properties = DialogProperties(
                        dismissOnClickOutside = false,
                    ),
                )
            }
        }

    }


}