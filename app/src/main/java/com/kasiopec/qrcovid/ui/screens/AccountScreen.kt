package com.kasiopec.qrcovid.ui.screens

import android.content.Intent
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.kasiopec.qrcovid.MainActivity
import com.kasiopec.qrcovid.NameEditField
import com.kasiopec.qrcovid.PrefsManager
import com.kasiopec.qrcovid.R
import com.kasiopec.qrcovid.app_components.AppBaseContainer
import com.kasiopec.qrcovid.ui.theme.QRCovidTheme

@Composable
fun AccountScreen(prefsManager: PrefsManager) {

    var editTextState by remember {
        mutableStateOf("")
    }
    var editTextError by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .padding(top = 64.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_new_user),
            contentDescription = null,
            Modifier
                .padding(vertical = 10.dp)
                .size(150.dp, 150.dp)
        )
        Text(
            stringResource(id = R.string.text_new_user),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(bottom = 64.dp)
        )
        NameEditField(editTextState, editTextError) {
            editTextState = it
        }
        Button(
            onClick = {
                if (editTextState.isEmpty()) {
                    editTextError = true
                    return@Button
                }
                prefsManager.setUserName(editTextState)
                val intent = Intent(context, MainActivity::class.java)
                startActivity(context, intent, null)
            },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                stringResource(id = R.string.button_continue),
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}