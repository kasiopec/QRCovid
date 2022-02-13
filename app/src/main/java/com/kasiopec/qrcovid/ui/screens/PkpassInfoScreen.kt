package com.kasiopec.qrcovid.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kasiopec.qrcovid.R
import com.kasiopec.qrcovid.app_components.AnnotatedClickableText
import com.kasiopec.qrcovid.app_components.BottomBarNavigator
import com.kasiopec.qrcovid.ui.theme.QRCovidTheme

@Composable
fun PkpassInfoScreen(navController: NavController) {

    val scaffoldState = rememberScaffoldState()
    QRCovidTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Certificate info",
                            color = MaterialTheme.colors.onPrimary,
                            style = MaterialTheme.typography.h5
                        )
                    },
                    backgroundColor = MaterialTheme.colors.primaryVariant
                )
            },
            bottomBar = {
                BottomBarNavigator(navController)
            },
            backgroundColor = MaterialTheme.colors.primaryVariant,
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState
        ) {
            MainScreenContent()
        }
    }
}

@Composable
fun ImageBlock() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_3x),
            "",
        )
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
                text = "Organization Name:",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onPrimary
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, bottom = 16.dp, end = 24.dp), Arrangement.SpaceBetween
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
                .padding(start = 24.dp, bottom = 16.dp, end = 24.dp), Arrangement.SpaceBetween
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp), Arrangement.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
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

@Composable
fun FooterBlock() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        Arrangement.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "This document was sourced from the link below, please visit for more information",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center
            )
            AnnotatedClickableText(
                url = stringResource(id = R.string.url_covid_certificate),
                annotationText = stringResource(id = R.string.annotation_url_covid_certificate),
                modifier = Modifier.padding(bottom = 32.dp, top = 8.dp),
                color = Color.Cyan
            )
        }
    }
}


@Composable
fun MainScreenContent() {
    Column(modifier = Modifier.fillMaxSize(), Arrangement.SpaceAround) {
        ImageBlock()
        InfoBlock()
        FooterBlock()
    }

}


@Preview
@Composable
fun PreviewComposable() {
    MainScreenContent()
}
