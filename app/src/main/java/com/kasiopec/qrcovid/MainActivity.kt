package com.kasiopec.qrcovid

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kasiopec.qrcovid.base_components.AppBaseContainer
import com.kasiopec.qrcovid.ui.theme.QRCovidTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var prefsManager: PrefsManager

    @Inject
    lateinit var qrView: QRView

    private var imageUriState = mutableStateOf<Uri?>(null)
    private var imageBitmap = mutableStateOf<ImageBitmap?>(null)

    private val selectImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            imageUriState.value = uri
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val qrCodeState = mutableStateOf(prefsManager.getCovidPassCode())

        setContent {
            val scaffoldState = rememberScaffoldState()
            val showDialog = remember { mutableStateOf(false) }
            QRCovidTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("My Certificate") },
                            backgroundColor = MaterialTheme.colors.primaryVariant
                        )
                    },
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    val userCovidPassCode = qrCodeState.value
                    val uri = imageUriState.value
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        MaterialTheme.colors.primary,
                                        MaterialTheme.colors.secondary
                                    )
                                )
                            ),
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
                                createImageBitmapFromUri(uri)
                                imageBitmap.value?.also { image ->
                                    QRCard(imageBitmap = image) {
                                        showDialog.value = it
                                    }
                                } ?: HandleNullCase(selectImageLauncher)
                            }
                            else -> NoQRCard(selectImageLauncher)
                        }
                    }
                }
            }
        }
    }

    private fun createImageBitmapFromUri(uri: Uri) {
        val bitmap: Bitmap?
        if (Build.VERSION.SDK_INT < 28) {
            bitmap = MediaStore.Images.Media.getBitmap(
                this@MainActivity.contentResolver,
                uri
            )
        } else {
            val bitmapSource =
                ImageDecoder.createSource(this@MainActivity.contentResolver, uri)
            bitmap = ImageDecoder.decodeBitmap(bitmapSource) { decoder, _, _ ->
                decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
                decoder.isMutableRequired = true
            }
        }

        bitmap?.let {
            imageBitmap.value = qrView.recreateQrCodeFromBitmap(it)
        }
    }
}

@Composable
fun HandleNullCase(launcher: ActivityResultLauncher<String>) {
    val context = LocalContext.current
    Toast.makeText(context, "Unable to decode the image", Toast.LENGTH_SHORT).show()
    NoQRCard(launcher)
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
            Button(
                onClick = { launcher.launch("image/*") },
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    stringResource(id = R.string.button_upload),
                    color = MaterialTheme.colors.onPrimary
                )
            }

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

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = {
            state(true)
        }) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_close_white_24),
                "",
                modifier = Modifier
                    .height(32.dp)
                    .width(32.dp)
            )
        }
    }
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

