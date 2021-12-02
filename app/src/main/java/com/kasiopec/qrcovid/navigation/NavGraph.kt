package com.kasiopec.qrcovid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kasiopec.qrcovid.PrefsManager
import com.kasiopec.qrcovid.QRView
import com.kasiopec.qrcovid.ui.screens.AccountScreen
import com.kasiopec.qrcovid.ui.screens.DocumentViewerScreen
import com.kasiopec.qrcovid.ui.screens.HomeScreen

@Composable
fun AppNavGraph(navController: NavHostController, prefsManager: PrefsManager, qrView: QRView) {
    NavHost(
        navController = navController,
        startDestination = if(prefsManager.getUserName()=="User"){
            BottomBarScreen.Account.route
        }else{
            BottomBarScreen.Home.route
        }
    ) {
        composable(BottomBarScreen.Account.route) {
            AccountScreen(
                navController = navController,
                prefsManager = prefsManager
            )
        }

        composable(BottomBarScreen.Home.route) {
            HomeScreen(
                prefsManager = prefsManager,
                qrView = qrView,
                navController = navController
            )
        }

        composable(BottomBarScreen.DocumentViewer.route) {
            DocumentViewerScreen(navController)
        }
    }
}