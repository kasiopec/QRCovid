package com.kasiopec.qrcovid.navigation

import androidx.annotation.DrawableRes
import com.kasiopec.qrcovid.R

sealed class BottomBarScreen(val route: String, val title: String, @DrawableRes val icon: Int) {
    object Home : BottomBarScreen(
        route = "home",
        title = "QR Cert",
        icon = R.drawable.ic_qr_code_scanner_24
    )

    object Account : BottomBarScreen(
        route = "account",
        title = "Account",
        icon = R.drawable.ic_account_box_24
    )

    object PkpassDetails : BottomBarScreen(
        route = "pkpass_details",
        title = "Info",
        icon = R.drawable.ic_document_24_black
    )
}
