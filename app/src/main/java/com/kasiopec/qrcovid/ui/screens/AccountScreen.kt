package com.kasiopec.qrcovid.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kasiopec.qrcovid.PrefsManager
import com.kasiopec.qrcovid.R
import com.kasiopec.qrcovid.app_components.BottomBar
import com.kasiopec.qrcovid.navigation.BottomBarScreen
import com.kasiopec.qrcovid.ui.theme.QRCovidTheme

@Composable
fun AccountScreen(navController: NavController, prefsManager: PrefsManager) {

    var editTextState by remember {
        mutableStateOf("")
    }
    var editTextError by remember {
        mutableStateOf(false)
    }
    val scaffoldState = rememberScaffoldState()

    QRCovidTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState,
            bottomBar = {

            }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .padding(top = 64.dp, bottom = 8.dp)
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
                        if (editTextState.isEmpty() || editTextState == "User") {
                            editTextError = true
                            return@Button
                        }
                        prefsManager.setUserName(editTextState)
                        navController.navigate(BottomBarScreen.Home.route) {
                            popUpTo(0)
                        }
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
    }
}


@Composable
fun NameEditField(value: String, isError: Boolean, updateString: (String) -> Unit) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        isError = isError,
        value = value,
        label = {
            if (isError) {
                Text(
                    stringResource(id = R.string.error_empty_name)
                )
            } else {
                Text(
                    stringResource(id = R.string.hint_user_name)
                )
            }
        },
        onValueChange = {
            updateString(it)
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
    )

}