package com.my.moneycounting.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.my.moneycounting.R
import com.my.moneycounting.data.Prefs


@Composable
fun StartStepScreen(onContinueClick: () -> Unit) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.bg), // Replace with the correct image resource ID
                    contentDescription = "Background Curve",
                    modifier = Modifier
                        .width(1000.dp)
                        .height(360.dp)
                        .align(Alignment.Center)
                )

                Image(
                    painter = painterResource(id = R.drawable.image_7), // Replace with the image of the button
                    contentDescription = "Continue Button",
                    modifier = Modifier
                        .padding(bottom = 70.dp)
                        .width(440.dp)
                        .height(240.dp)
                        .align(Alignment.BottomCenter)

                )
            }

            // Top Section: Expenses List
            Image(
                painter = painterResource(id = R.drawable.text3), // Replace with the correct image resource ID
                contentDescription = "Your Expenses",
                modifier = Modifier
                    .padding(horizontal = 0.dp)
                    .width(340.dp)
                    .height(190.dp)
            )

            // Middle Section: Title and Description
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.bt_3), // Replace with the correct image resource ID
                contentDescription = "Expense/Income Calculation",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .height(62.dp)
                    .width(220.dp)
                    .clickable {
                        Prefs.startStepCompleted = true
                        onContinueClick()
                    }
            )

            Spacer(modifier = Modifier.height(35.dp))
        }
    }
}