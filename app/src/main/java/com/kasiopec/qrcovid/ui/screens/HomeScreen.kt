package com.kasiopec.qrcovid.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kasiopec.qrcovid.*
import com.kasiopec.qrcovid.R
import com.kasiopec.qrcovid.app_components.BottomBarNavigator
import com.kasiopec.qrcovid.ui.theme.QRCovidTheme

@Composable
fun HomeScreen(prefsManager: PrefsManager, qrView: QRView, navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    val showDialog = remember { mutableStateOf(false) }
    val qrCodeState = remember { mutableStateOf(prefsManager.getCovidPassCode()) }
    val imageUriState = remember { mutableStateOf<Uri?>(null) }
    val imageBitmap = remember { mutableStateOf<ImageBitmap?>(null) }
    val context = LocalContext.current
    val selectImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            imageUriState.value = uri
        }
    val userCovidPassCode = qrCodeState.value
    val uri = imageUriState.value
    QRCovidTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colors.onPrimary,
                            text = "My Certificate",
                            textAlign = TextAlign.Center
                        )
                    },
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp
                )
            },
            bottomBar = {
                if (prefsManager.getUserName() != "User") {
                    BottomAppBar(
                        elevation = 5.dp,
                        modifier = Modifier
                            .height(65.dp)
                            .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
                        cutoutShape = CircleShape,
                    ) {
                        BottomBarNavigator(
                            navController = navController
                        )
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            floatingActionButton = {
                if (userCovidPassCode != null || uri != null) {
                    FloatingDeleteButton {
                        showDialog.value = it
                    }
                } else {
                    FloatingAddQRCodeButton(launcher = selectImageLauncher)
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colors.primaryVariant
//                    brush = Brush.verticalGradient(
//                        colors = listOf(
//                            MaterialTheme.colors.primaryVariant,
//                            MaterialTheme.colors.onPrimary
//                        )
//                    )
                ),
            scaffoldState = scaffoldState,
            backgroundColor = Color.Transparent
        ) { innerPadding ->
            Box(
                modifier = Modifier.padding(
                    PaddingValues(
                        0.dp,
                        0.dp,
                        0.dp,
                        innerPadding.calculateBottomPadding()
                    )
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    UserNameContainer(name = prefsManager.getUserName())
                    if (showDialog.value) {
                        AlertDialogBox(
                            name = "Delete",
                            description = "Do you want to delete this QR code?",
                            showDialog = showDialog.value,
                            onDismiss = {
                                showDialog.value = false
                            }, onConfirm = {
                                prefsManager.removeCovidPassCode()
                                qrCodeState.value = null
                                imageUriState.value = null
                                showDialog.value = false
                            })
                    }
                    when {
                        userCovidPassCode != null -> QRCard(
                            imageBitmap = qrView.generateQrImage(userCovidPassCode)
                        ) {
                            showDialog.value = it
                        }
                        uri != null -> {
                            imageBitmap.value = createImageBitmapFromUri(context, uri, qrView)
                            imageBitmap.value?.also { image ->
                                QRCard(imageBitmap = image) {
                                    showDialog.value = it
                                }
                            } ?: HandleNullCase(selectImageLauncher)
                        }
                        else -> NoQRCard(selectImageLauncher)
                    }
                    //BottomBarMain(navController = navController, prefsManager = prefsManager)
                }
            }
        }
    }
}

fun createImageBitmapFromUri(context: Context, uri: Uri, qrView: QRView): ImageBitmap? {
    val bitmap: Bitmap?
    if (Build.VERSION.SDK_INT < 28) {
        bitmap = MediaStore.Images.Media.getBitmap(
            context.contentResolver,
            uri
        )
    } else {
        val bitmapSource =
            ImageDecoder.createSource(context.contentResolver, uri)
        bitmap = ImageDecoder.decodeBitmap(bitmapSource) { decoder, _, _ ->
            decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
            decoder.isMutableRequired = true
        }
    }

    return bitmap?.let {
        qrView.recreateQrCodeFromBitmap(it)
    }
}


@Composable
fun HandleNullCase(launcher: ActivityResultLauncher<String>) {
    val context = LocalContext.current
    Toast.makeText(context, "Unable to decode the image", Toast.LENGTH_SHORT).show()
    NoQRCard(launcher)
}

/**
 * Returns floating action composable
 * with the state that controls deletion dialog box
 * */
@Composable
fun FloatingDeleteButton(state: (Boolean) -> Unit) {
    FloatingActionButton(
        onClick = { state(true) },
        shape = CircleShape,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_close_white_24),
            "",
            modifier = Modifier
                .height(32.dp)
                .width(32.dp)
        )
    }
}

@Composable
fun FloatingAddQRCodeButton(launcher: ActivityResultLauncher<String>) {
    FloatingActionButton(
        onClick = { launcher.launch("image/*") },
        shape = CircleShape,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_qr_code_scanner_white_24),
            "",
            modifier = Modifier
                .height(32.dp)
                .width(32.dp),
        )
    }
}

@Composable
fun UserNameContainer(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            stringResource(id = R.string.user_greetings, name),
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onPrimary
        )
    }

}

@Composable
fun NoQRCard(launcher: ActivityResultLauncher<String>) {
    Card(
        elevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(id = R.string.text_no_qr_heading),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4
            )
            Image(
                painter = painterResource(id = R.drawable.ic_empty),
                contentDescription = null,
                Modifier.padding(vertical = 10.dp)
            )
            Text(
                stringResource(id = R.string.text_no_qr_body_1),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 16.dp)

            )
            Text(
                stringResource(id = R.string.text_no_qr_body_2),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1,

                )
            AnnotatedClickableText(
                url = stringResource(id = R.string.url_covid_certificate),
                annotationText = stringResource(id = R.string.annotation_url_covid_certificate),
                modifier = Modifier.padding(bottom = 32.dp, top = 8.dp)
            )
//            Button(
//                onClick = { launcher.launch("image/*") },
//                shape = MaterialTheme.shapes.medium
//            ) {
//                Text(
//                    stringResource(id = R.string.button_upload),
//                    color = MaterialTheme.colors.onPrimary
//                )
//            }

        }
    }
}


@Composable
fun QRCard(imageBitmap: ImageBitmap, state: (Boolean) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(
            stringResource(id = R.string.text_qr_heading),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(bottom = 16.dp),
            color = MaterialTheme.colors.onPrimary

        )
    }
    Card(
        elevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                bitmap = imageBitmap,
                contentDescription = null,
                Modifier
                    .padding(vertical = 10.dp)
            )
        }
    }

//    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
//        IconButton(onClick = {
//            state(true)
//        }) {
//            Image(
//                painter = painterResource(id = R.drawable.ic_baseline_close_white_24),
//                "",
//                modifier = Modifier
//                    .height(32.dp)
//                    .width(32.dp)
//            )
//        }
//    }
}

@Composable
fun AlertDialogBox(
    name: String,
    description: String,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            title = {
                Text(text = name)
            },
            text = {
                Text(text = description)
            },
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            })
    }
}


@Composable
fun AnnotatedClickableText(url: String, annotationText: String, modifier: Modifier) {
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
                color = Color.Blue,
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