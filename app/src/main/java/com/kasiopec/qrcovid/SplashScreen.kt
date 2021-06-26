package com.kasiopec.qrcovid

import android.content.Intent
import android.os.Bundle
import android.util.FloatProperty
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kasiopec.qrcovid.components.AppBaseContainer

class SplashScreen : ComponentActivity() {
    private val prefsManager = PrefsManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            var sizeState by remember {
                mutableStateOf(50.dp)
            }
            val size by animateDpAsState(
                targetValue = sizeState,
                animationSpec = tween(1500, delayMillis = 300),
                finishedListener = {
                    val intent : Intent = if(prefsManager.getUserName()=="User"){
                        Intent(this, NameActivity::class.java)
                    }else{
                        Intent(this, MainActivity::class.java)
                    }
                    startActivity(intent)
                    finish()
                }
            )
            AppBaseContainer {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Image(
                        painter = painterResource(id = R.drawable.logo_transparent),
                        contentDescription = null,
                        Modifier
                            .padding(vertical = 10.dp)
                            .size(size)
                    )
                }
                sizeState = 500.dp
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.logo_transparent),
            contentDescription = null,
            Modifier
                .padding(vertical = 10.dp)
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                )
        )
    }
}