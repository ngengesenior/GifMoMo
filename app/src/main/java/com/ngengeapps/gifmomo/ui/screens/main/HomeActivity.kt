package com.ngengeapps.gifmomo.ui.screens.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import com.ngengeapps.gifmomo.R
import com.ngengeapps.gifmomo.ui.screens.donation_ui.DonationActivity

@ExperimentalFoundationApi
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            HomeUI(onNavigate = {  }) {

                startActivity(Intent(this@HomeActivity,DonationActivity::class.java))
            }
        }
    }
}