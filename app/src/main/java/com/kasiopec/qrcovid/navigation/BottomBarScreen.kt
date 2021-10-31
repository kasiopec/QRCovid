package com.kasiopec.qrcovid.navigation

import androidx.annotation.DrawableRes
import com.kasiopec.qrcovid.R

sealed class BottomBarScreen(val route: String, val title: String, @DrawableRes val icon: Int) {
    object Home : BottomBarScreen(
        route = "home",
        title = "QR Certificate",
        icon = R.drawable.ic_qr_code_scanner_24
    )

    object Account : BottomBarScreen(
        route = "account",
        title = "My Account",
        icon = R.drawable.ic_account_box_24
    )

    object DocumentViewer : BottomBarScreen(
        route = "documentViewer",
        title = "Vaccine certeficate",
        icon = R.drawable.ic_document_24_black
    )
}
