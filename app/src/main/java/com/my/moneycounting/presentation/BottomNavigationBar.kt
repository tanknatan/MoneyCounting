package com.my.moneycounting.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.my.moneycounting.R

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    selectedItem: String,
    onSettingsClick: () -> Unit,
    onReportClick: () -> Unit,
    onBankClick: () -> Unit,
    onNotificationClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp) // Add padding from the bottom of the screen
            .padding(horizontal = 48.dp)
            .height(64.dp) // Set the height of the navigation bar
            .background(
                color = Color(0x9EA2A699),
                shape = RoundedCornerShape(50.dp) // Rounded corners
            ),
        horizontalArrangement = Arrangement.SpaceAround, // Distribute items evenly
        verticalAlignment = Alignment.CenterVertically
    ) {
        val items = listOf(
            Pair(R.drawable.ic_report, "Report"),
            Pair(R.drawable.ic_bank, "Bank"),
            Pair(R.drawable.ic_notification_act, "Notifications"),
            Pair(R.drawable.ic_settings, "Settings")
        )

        items.forEach { (imageRes, label) ->
            val isSelected = label == selectedItem

            IconButton(
                modifier = Modifier
                    .size(45.dp)
                    .background(
                        if (isSelected) Color(0xFFFCF485) else black,
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                onClick = {
                        when (label) {
                            "Report" -> onReportClick()
                            "Bank" -> onBankClick()
                            "Settings" -> onSettingsClick()
                            "Notifications" -> onNotificationClick()
                        }
                    },
            ) {
                Icon(
                    painter = painterResource(id = imageRes),
                    contentDescription = label,
                    modifier = Modifier.size(33.dp), // Adjust the size to fit within the background,
                    tint = if (isSelected) black else Color.White,
                )
            }
        }
    }
}