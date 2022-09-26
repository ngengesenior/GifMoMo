package com.ngengeapps.gifmomo.ui

import android.app.Application
import com.ngengeapps.gifmomo.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import net.compay.android.CamPay

@HiltAndroidApp
class MainHiltApp : Application(){
    override fun onCreate() {
        super.onCreate()
        CamPay.init(
            BuildConfig.CAM_PAY_USER_NAME,
            BuildConfig.CAM_PAY_PASSWORD,
            CamPay.Environment.DEV
        )
    }
}