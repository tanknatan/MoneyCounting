package com.my.moneycounting.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
            .background(Color.Black)
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
        BottomNavigationBar4(
            onItemSelected = { selectedItem ->
                // Handle generic item selection if needed
            },
            onNotificationClick = {
                onNotificationClick()
            },
            onReportClick = {
                onReportClick()
            },
            onBankClick = {
                onBankClick()
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
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_right), // Replace with your arrow image resource
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun BottomNavigationBar4(
    onItemSelected: (String) -> Unit,
    onReportClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onBankClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp) // Add padding from the bottom of the screen
            .padding(horizontal = 60.dp)
            .height(64.dp) // Set the height of the navigation bar
            .background(
                color = Color.Gray,
                shape = RoundedCornerShape(50.dp) // Rounded corners
            ),
        horizontalArrangement = Arrangement.SpaceEvenly, // Distribute items evenly
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Replace each icon with your images
        val items = listOf(
            Pair(R.drawable.ic_report, "Report"),
            Pair(R.drawable.ic_bank, "Bank"),
            Pair(R.drawable.ic_notification, "Notifications"),
            Pair(R.drawable.ic_settings_act, "Settings")
        )

        items.forEach { (imageRes, label) ->

            // Check if it's the selected item to highlight it
            val isSelected = label == "Settings" // Example: Highlight the "Settings" item

            IconButton(
                modifier = Modifier
                    .size(45.dp) // Adjust size to match the rounded background
                    .background(
                        if (isSelected) Color(0xFFFCF485) else Color.Black, // Highlight the selected item
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                onClick =  {
                        when (label) {
                            "Report" -> onReportClick()
                            "Bank" -> onBankClick()
                            "Notifications" -> onNotificationClick()
                            else -> onItemSelected(label)
                        }
                    }
            ) {
                Icon(
                    painter = painterResource(id = imageRes),
                    contentDescription = label,
                    modifier = Modifier.size(33.dp), // Adjust the size to fit within the background,
                    tint = Color.Unspecified
                )
            }
        }
    }
}
