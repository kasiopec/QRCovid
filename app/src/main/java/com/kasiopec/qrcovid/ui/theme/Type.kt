package com.kasiopec.qrcovid.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kasiopec.qrcovid.R

val fontsNexa = FontFamily(
    Font(R.font.nexa_regular, weight = FontWeight.Normal),
    Font(R.font.nexa_regular_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.nexa_bold, weight = FontWeight.Bold),
    Font(R.font.nexa_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.nexa_light, weight = FontWeight.Light),
    Font(R.font.nexa_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(R.font.nexa_thin, weight = FontWeight.Thin),
    Font(R.font.nexa_thin_italic, weight = FontWeight.Thin, style = FontStyle.Italic)

)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = fontsNexa,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    h5 = TextStyle(
        fontFamily = fontsNexa,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    h4 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp
    ),
    h3 = TextStyle(
        fontFamily = fontsNexa,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp
    )
    /* Other default text styles to override

caption = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
)
*/
)