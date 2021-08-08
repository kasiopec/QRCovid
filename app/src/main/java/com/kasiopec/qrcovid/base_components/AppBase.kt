package com.kasiopec.qrcovid.base_components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kasiopec.qrcovid.ui.theme.QRCovidTheme

/**
 * We are creating container that can be used on other screens by using the same theme
 * We also passing generalized content and then calling it via content() call
 * **/
@Composable
fun AppBaseContainer(content: @Composable () -> Unit) {
    val scaffoldState = rememberScaffoldState()
    QRCovidTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState
        ) {
            content()
        }
    }
}