package com.my.moneycounting.presentayion

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.my.moneycounting.R
import kotlinx.coroutines.delay


@Composable
fun LoadingScreen(onTimeout: () -> Unit) {
    val rotation = remember { Animatable(0f) }
    val totalRotations = 1
    val rotationDuration = 1100 // milliseconds

    LaunchedEffect(Unit) {
        repeat(totalRotations) {
            rotation.animateTo(
                targetValue = rotation.value + 360f,
                animationSpec = tween(
                    durationMillis = rotationDuration,
                    easing = LinearEasing
                )
            )
        }
        delay(100) // Short delay before transitioning
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.loading_circle),
                contentDescription = "Loading Circle",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .graphicsLayer {
                        rotationZ = rotation.value
                    }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Loading",
                fontSize = 24.sp,
                color = Color.White
            )
        }
    }
}
