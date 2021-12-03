package com.kasiopec.qrcovid.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.kasiopec.qrcovid.app_components.BottomBar
import com.kasiopec.qrcovid.ui.theme.QRCovidTheme

@Composable
fun DocumentViewerScreen(navController: NavController) {

    val scaffoldState = rememberScaffoldState()
    QRCovidTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("My Certificate") },
                    backgroundColor = MaterialTheme.colors.primaryVariant
                )
            },
            bottomBar = {
                BottomBar(navController)
            },
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Doc viewer")
            }
        }
    }
}