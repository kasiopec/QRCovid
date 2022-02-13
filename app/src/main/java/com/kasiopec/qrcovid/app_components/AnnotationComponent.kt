package com.kasiopec.qrcovid.app_components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun AnnotatedClickableText(url: String, annotationText: String, modifier: Modifier, color: Color) {
    val uriHandler = LocalUriHandler.current
    val annotatedText = buildAnnotatedString {
        // We attach this *URL* annotation to the following content
        // until `pop()` is called
        pushStringAnnotation(
            tag = "URL",
            annotation = url
        )
        withStyle(
            style = SpanStyle(
                color = color,
                fontWeight = FontWeight.Bold
            )
        ) {
            append(annotationText)
        }

        pop()
    }

    ClickableText(
        text = annotatedText,
        style = MaterialTheme.typography.body1,
        modifier = modifier,
        onClick = { offset ->
            // We check if there is an *URL* annotation attached to the text
            // at the clicked position
            annotatedText.getStringAnnotations(
                tag = "URL",
                start = offset,
                end = offset
            )
                .firstOrNull()?.let { annotation ->
                    uriHandler.openUri(annotation.item)
                }
        }
    )
}
