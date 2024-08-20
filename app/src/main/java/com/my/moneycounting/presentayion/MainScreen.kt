package com.my.moneycounting.presentayion

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.my.moneycounting.R

@Composable
fun MainScreen(onBackClick: () -> Unit, onSettingsClick: () -> Unit, onNotificationClick: () -> Unit, onBankClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Status Bar
        StatusBar1(onBackClick = {
            onBackClick()
        })

        Spacer(modifier = Modifier.weight(1f))

        // Your main content goes here (e.g., expenses list)

        // Bottom Navigation Bar
        BottomNavigationBar1(
            onItemSelected = { selectedItem ->
                // Handle generic item selection if needed
            },
            onSettingsClick = {
                onSettingsClick()
            },
            onNotificationClick = {
                onNotificationClick()
            },
            onBankClick = {
                onBankClick()
            }
        )
    }
}

@Composable
fun StatusBar1(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back Button
        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back Button",

            modifier = Modifier.size(40.dp) // Adjust the size to fit within the background
                .clickable { onBackClick() }
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Title
        Text(
            text = "Your expenses",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 70.dp)


        )
    }
}

@Composable
fun BottomNavigationBar1(
    onItemSelected: (String) -> Unit,
    onSettingsClick: () -> Unit,
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
            Pair(R.drawable.ic_report_act, "Report"),
            Pair(R.drawable.ic_bank, "Bank"),
            Pair(R.drawable.ic_notification, "Notifications"),
            Pair(R.drawable.ic_settings, "Settings")
        )

        items.forEach { (imageRes, label) ->

            // Check if it's the selected item to highlight it
            val isSelected = label == "Report" // Example: Highlight the "Report" item

            Box(
                modifier = Modifier
                    .size(45.dp) // Adjust size to match the rounded background
                    .background(
                        if (isSelected) Color(0xFFFCF485) else Color.Transparent, // Highlight the selected item
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
                    .clickable {
                        when (label) {
                            "Report" -> onNotificationClick()
                            "Bank" -> onBankClick()
                            "Settings" -> onSettingsClick()
                            else -> onItemSelected(label)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = label,
                    modifier = Modifier.size(49.dp) // Adjust the size to fit within the background
                )
            }
        }
    }
}


@Composable
fun ExpenseItem(iconRes: Int, label: String, amount: Int, percentage: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = "$label Icon",
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "$$amount",
            color = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "$percentage%",
            color = Color.Gray
        )
    }
}