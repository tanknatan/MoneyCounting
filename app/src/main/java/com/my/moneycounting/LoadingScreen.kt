package com.my.moneycounting

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun LoadingScreen(
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LoadingAnimation()
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Loading",
                fontSize = 24.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun LoadingAnimation() {
    val dotSize = 16.dp
    val delayBetweenDots = 300L

    val colors = listOf(
        Color(0xFFFFF176), // Light yellow
        Color(0xFFFFEE58), // Yellow
        Color(0xFFFFD600)  // Darker yellow
    )

    val infiniteTransition = rememberInfiniteTransition()

    val animatedDots = (0..7).map { index ->
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = (colors.size * delayBetweenDots).toInt()
                    0f at 0 with LinearEasing
                    1f at (colors.size * delayBetweenDots).toInt() with LinearEasing
                },
                repeatMode = RepeatMode.Restart,
                initialStartOffset = StartOffset((index * delayBetweenDots).toInt())
            )
        )
    }

    Row(
        modifier = Modifier.size(100.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        animatedDots.forEachIndexed { index, animation ->
            Box(
                modifier = Modifier
                    .size(dotSize)
                    .background(colors[(index + animation.value.toInt()) % colors.size])
                    .padding(4.dp)
            )
        }
    }
}