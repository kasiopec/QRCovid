package com.kasiopec.qrcovid.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.kasiopec.qrcovid.app_components.BottomBarNavigator
import com.kasiopec.qrcovid.ui.theme.QRCovidTheme

@Composable
fun PkpassInfoScreen(navController: NavController) {

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
                BottomBarNavigator(navController)
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


@Composable
fun InfoBlock(){
    Column(){
        Text(text = "Description:")
        Text(text = "Organization Name:")
        Text(text = "Vaccination day:")
        Text(text = "User name:")
        Text(text = "Birthday date:")
        Text(text = "Vaccine dose:")
    }

}


@Preview
@Composable
fun PreviewComposable(){
    InfoBlock()
}
