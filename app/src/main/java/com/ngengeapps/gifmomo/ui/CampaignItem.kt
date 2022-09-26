package com.ngengeapps.gifmomo.ui

import android.widget.ImageView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.ngengeapps.gifmomo.R
import com.ngengeapps.gifmomo.model.Campaign
import com.ngengeapps.gifmomo.model.fakeCampaign
import com.ngengeapps.gifmomo.ui.theme.GifMoMoTheme

@Composable
fun CampaignItem(item:Campaign,onClick:()->Unit,modifier: Modifier = Modifier){
    Card(modifier = modifier
        .fillMaxWidth()
        .height(200.dp)
        .clickable {
            onClick()
        }, border = BorderStroke(.5.dp, color = Color.Black)
    ) {
        Column(modifier = Modifier.padding(horizontal = 4.dp)){
            Image(painter = painterResource(id = R.drawable.ic_round_image), contentDescription = "" ,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = item.title,
                style = MaterialTheme.typography.subtitle2)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "XAF ${item.currentRaised} of XAF ${item.targetAmount}")
            Spacer(modifier = Modifier.height(2.dp))
            Box {
                LinearProgressIndicator(color = MaterialTheme.colors.primary.copy(alpha = 0.3f),
                progress = 1f)
                LinearProgressIndicator(
                    progress = (item.currentRaised/item.targetAmount).toFloat(),
                    color = MaterialTheme.colors.primary
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
        }
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CampaignItemPreview(){
    GifMoMoTheme {
        CampaignItem(item = fakeCampaign, onClick = { /*TODO*/ })

    }
}