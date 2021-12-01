package com.kasiopec.qrcovid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.*
import androidx.navigation.compose.rememberNavController
import com.kasiopec.qrcovid.app_components.AppBaseContainer
import com.kasiopec.qrcovid.navigation.AppNavGraph
import com.kasiopec.qrcovid.ui.screens.UserNameContainer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var prefsManager: PrefsManager

    @Inject
    lateinit var qrView: QRView
    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            AppNavGraph(
                navController = navController,
                prefsManager = prefsManager,
                qrView = qrView
            )
        }
    }


}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppBaseContainer {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            UserNameContainer(name = "Unknown")
            //AlertDialogBox(name = "Test", description = "Test", showDialog = true, onDismiss = {}, onConfirm = {})
        }
    }
}

