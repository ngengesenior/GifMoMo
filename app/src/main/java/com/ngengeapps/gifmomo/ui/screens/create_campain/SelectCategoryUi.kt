package com.ngengeapps.gifmomo.ui.screens.create_campain

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@ExperimentalMaterialApi
//@Preview(showSystemUi = true, showBackground = true)
@ExperimentalFoundationApi
@Composable
fun SelectCategoryUi(
    viewModel: CampaignViewModel,
    closeDialog: () -> Unit = {}
) {

    val selectedIndex by viewModel.selectedCategoryIndex.collectAsState(initial = -1 to "")

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Select Category",

                    )
            },
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 0.dp,
            actions = {
                OutlinedButton(onClick = { closeDialog() }) {
                    Text(text = "Done")
                }
            }
        )

    }) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(3.dp)
                .wrapContentWidth()
        ) {
            itemsIndexed(
                items = viewModel.categories,
                key = { _: Int, value: Pair<Int, String> -> value },
                contentType = { index: Int, item: Any -> "$index$item" },
                itemContent = { index: Int, item: Pair<Int, String> ->

                    FilterChip(
                        selected = selectedIndex.first == index, onClick = {

                            if (selectedIndex.first == index) {
                                viewModel.resetSelected()
                            } else {
                                viewModel.selectIndex(item.first, item.second)
                            }
                        },
                        content = {
                            Text(text = item.second)
                        },
                        selectedIcon = {
                            Icon(Icons.Filled.Check, contentDescription = "")
                        },
                        colors = ChipDefaults.filterChipColors(selectedContentColor = MaterialTheme.colors.primary),
                        border = if (selectedIndex.first == index) BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colors.primary
                        ) else null,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                })
        }


    }


}