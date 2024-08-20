package com.my.moneycounting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun WelcomeScreen(onStartClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Replacing the text with the image
            Image(
                painter = painterResource(id = R.drawable.welcome_text), // Replace with the correct image resource ID
                contentDescription = "Explore Our Financial Tools",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 80.dp, top = 280.dp)
                    .size(60.dp)
            )
            Spacer(modifier = Modifier.height(100.dp))
            Button(
                onClick = onStartClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFCF485), // Updated to use the specified yellow color
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .height(56.dp)
                    .width(200.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Start",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = "Arrow",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
