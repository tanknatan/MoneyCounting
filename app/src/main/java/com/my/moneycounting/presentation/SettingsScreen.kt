package com.my.moneycounting.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.my.moneycounting.R


@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onReportClick: () -> Unit,
    onBankClick: () -> Unit,
    onTermsClick: () -> Unit, // New parameter for the "Terms and Conditions" button
    onPrivacyClick: () -> Unit // New parameter for the "Privacy Policy" button
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(black)
    ) {
        // Status Bar
        StatusBar(
            info = "Settings",
            onBackClick = {
            onBackClick()
        })

        Spacer(modifier = Modifier.weight(1f))
        SettingsButton(
            text = "Terms and conditions",
            onClick = {onTermsClick()}
        )
        Spacer(modifier = Modifier.height(16.dp))
        SettingsButton(
            text = "Privacy policy",
            onClick = {onPrivacyClick()}
        )

        Spacer(modifier = Modifier.weight(1f))

        // Bottom Navigation Bar
        BottomNavigationBar(
            selectedItem = "Settings",
            onSettingsClick = {},
            onReportClick = {
                onReportClick()
            },
            onBankClick = {
                onBankClick()
            },
            onNotificationClick = {
                onNotificationClick()
            }
        )
    }
}



@Composable
fun SettingsButton(text: String, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .background(
                color = Color(0xFFFCF485),
                shape = RoundedCornerShape(50.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                color = black,
                fontWeight = FontWeight.Black
            )
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_right), // Replace with your arrow image resource
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}