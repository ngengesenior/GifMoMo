package com.ngengeapps.gifmomo

import android.content.res.Resources
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.ngengeapps.gifmomo.common.snackbar.SnackbarManager
import com.ngengeapps.gifmomo.ui.screens.create_campain.CampaignViewModel
import com.ngengeapps.gifmomo.ui.screens.create_campain.CreateCampaignUi
import com.ngengeapps.gifmomo.ui.screens.create_campain.SelectCategoryUi
import com.ngengeapps.gifmomo.ui.screens.donation_ui.DonationUI
import com.ngengeapps.gifmomo.ui.screens.main.HomeUI
import com.ngengeapps.gifmomo.ui.screens.phone_login.PhoneLoginUI
import com.ngengeapps.gifmomo.ui.screens.sign_up.AuthViewModel
import com.ngengeapps.gifmomo.ui.screens.sign_up.SignUpUI
import com.ngengeapps.gifmomo.ui.screens.splash.SplashScreenUI
import com.ngengeapps.gifmomo.ui.screens.splash.SplashViewModel
import com.ngengeapps.gifmomo.ui.theme.GifMoMoTheme
import kotlinx.coroutines.CoroutineScope

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun GifMoMoApp(authViewModel: AuthViewModel) {
    GifMoMoTheme {

    }
}

/**
 * Surface(color = MaterialTheme.colors.background) {
val appState = rememberAppState()
Scaffold(
scaffoldState = appState.scaffoldState,
snackbarHost = {
SnackbarHost(hostState = it,
modifier = Modifier.padding(8.dp),
snackbar = { snackbarData ->
Snackbar(snackbarData, contentColor = MaterialTheme.colors.onPrimary)
})
}) { innerPadding ->

val splashVm:SplashViewModel = hiltViewModel()
val categoryViewModel: CampaignViewModel = hiltViewModel()
val createVm:CampaignViewModel = hiltViewModel()

NavHost(
navController = appState.navController,
modifier = Modifier.padding(innerPadding),
startDestination = SPLASH_SCREEN
) {

composable(LOGIN_SCREEN) {
PhoneLoginUI(navController = appState.navController,
viewModel = authViewModel, restartLogin = {
appState.clearAndNavigate(SPLASH_SCREEN)
}, destination = MAIN_SCREEN)
}

composable(MAIN_SCREEN) {
HomeUI(onNavigate = {
appState.navigate(CREATE_RAISER_SCREEN)
}, onNavigateToPay = {
appState.navigate(PAYMENT_SCREEN)
})
}


composable(SIGN_UP_SCREEN) {
SignUpUI(popUpScreen = {
appState.clearAndNavigate(MAIN_SCREEN)

})
}

composable(PAYMENT_SCREEN){
DonationUI()
}

composable(CREATE_RAISER_SCREEN) {
CreateCampaignUi(viewModel =createVm, onCreateSuccess = {
appState.navController.navigateUp()

}, openCategoryRoute = {} )


}
dialog(
SELECT_CATEGORY_DIALOG,
dialogProperties = DialogProperties(
dismissOnBackPress = true,
dismissOnClickOutside = false,
usePlatformDefaultWidth = true
)
) {
SelectCategoryUi(closeDialog = {
appState.popUp()
}, viewModel = categoryViewModel)
}

}

}

}
 */

@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(scaffoldState, navController, snackbarManager, resources, coroutineScope) {
    GifMoMoAppState(scaffoldState, navController, snackbarManager, coroutineScope, resources)
}

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

