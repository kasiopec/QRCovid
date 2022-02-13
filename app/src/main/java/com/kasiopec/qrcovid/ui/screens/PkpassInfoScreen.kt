package com.kasiopec.qrcovid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
fun InfoBlock() {
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            Arrangement.Center
        ) {
            Text(
                text = "Description:",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onPrimary
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            Arrangement.Center
        ) {
            Text(
                text = "Organization Name:",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onPrimary
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Blue)
                .padding(start = 16.dp, bottom = 16.dp, end = 16.dp), Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = "Name",
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onPrimary
                )
                Text(
                    text = "Pavels Papsujevics",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary
                )
            }
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = "Birthday date",
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onPrimary
                )
                Text(
                    text = "29.11.1993",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Blue)
                .padding(start = 16.dp, bottom = 16.dp, end = 16.dp), Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = "Vaccination day",
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onPrimary
                )
                Text(
                    text = "22.06.2021",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary
                )
            }
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = "Vaccine dose",
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onPrimary
                )
                Text(
                    text = "2/2",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), Arrangement.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Text(
                    text = "Identifier",
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onPrimary
                )
                Text(
                    text = "hhasdjhadh:sahd:233:4231",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }

}


@Preview
@Composable
fun PreviewComposable() {
    InfoBlock()
}
