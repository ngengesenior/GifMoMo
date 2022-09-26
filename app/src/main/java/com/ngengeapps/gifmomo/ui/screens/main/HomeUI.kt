package com.ngengeapps.gifmomo.ui.screens.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ngengeapps.gifmomo.model.fakeCampaign
import com.ngengeapps.gifmomo.ui.CampaignItem


@ExperimentalFoundationApi
@Composable
fun HomeUI(
    onNavigate:()->Unit,
    onNavigateToPay:()->Unit
) {
    Scaffold(topBar = {
        OutlinedTextField(value = "", onValueChange ={

        }, trailingIcon = {
            Icon(Icons.Default.Search,"")
        },
            label = {
                Text(text = "Search")
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
            ,
            singleLine = true
        )
    },
    floatingActionButton = {
        ExtendedFloatingActionButton(text = { Text(text = "Start campaign") }, onClick = onNavigate,
        icon = {Icon(Icons.Default.Add,null)})
    }) {

        LazyColumn(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {

            items(count = 15){
                CampaignItem(item = fakeCampaign, onClick = onNavigateToPay)
                Spacer(modifier = Modifier.height(6.dp))
                Divider()
            }


        }

    }



}