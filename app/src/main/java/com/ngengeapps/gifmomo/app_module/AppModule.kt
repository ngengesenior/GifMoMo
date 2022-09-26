package com.ngengeapps.gifmomo.app_module

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngengeapps.gifmomo.MainActivity
import com.ngengeapps.gifmomo.common.snackbar.SnackbarManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.compay.android.CamPay
import javax.inject.Singleton

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth


    @Singleton
    @Provides
    fun provideSnackbarManager(): SnackbarManager = SnackbarManager

    @Singleton
    @Provides
    fun provideMainActivity(): MainActivity = MainActivity.getInstance() as MainActivity

    @Singleton
    @Provides
    fun provideCamPay():CamPay = CamPay.getInstance()

}