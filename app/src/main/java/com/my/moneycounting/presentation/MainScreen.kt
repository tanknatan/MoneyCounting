package com.my.moneycounting.presentation

import android.text.TextUtils
import android.util.Log
import androidx.compose.runtime.*

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.my.moneycounting.R
import com.my.moneycounting.data.Transaction
import com.my.moneycounting.data.TransactionDao

class TransactionViewModel(private val dao: TransactionDao) : ViewModel() {
    val transactions: State<List<Transaction>> = dao.getTransactionsByType("Expense").collectAsState(initial = emptyList())

    // You can also add functions to add new transactions, delete them, etc.
}
@Composable
fun MainScreen(viewModel: TransactionViewModel = viewModel()) {
    val transactions by viewModel.transactions

    val donutChartData = PieChartData(
        slices = currentItems.map {
            PieChartData.Slice(
                label = it.title,
                value = ((it.amount.toFloat() / currentItems.sumOf { it.amount }) * 100).also {
                    Log.d("TAG", "donutChartData: $it")
                },
                color = Color(it.color)
            )
        },
        plotType = PlotType.Pie
    )
    val donutChartConfig = PieChartConfig(
        labelVisible = true,
        labelFontSize = 32.sp,
        strokeWidth = 120f,
        labelColor = Color.Black,
        activeSliceAlpha = 0.9f,
        isAnimationEnable = true,
        backgroundColor = MainBlue,
        showSliceLabels = true,
        sliceLabelEllipsizeAt = TextUtils.TruncateAt.END
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            StatusBar(            info = "Your expenses",
                onBackClick = {
                    onBackClick()
                })
            Spacer(modifier = Modifier.height(16.dp))
            DonutPieChart(modifier = Modifier
                .size(220.dp),
                pieChartData = donutChartData,
                pieChartConfig = donutChartConfig)
            Spacer(modifier = Modifier.height(16.dp))
            PeriodSelectionButtons()
            Spacer(modifier = Modifier.height(16.dp))
            IncomeExpenseToggle()
            Spacer(modifier = Modifier.height(16.dp))
            AddOperationButton()
            Spacer(modifier = Modifier.height(16.dp))
            ExpenseList(transactions)
        }

        // BottomNavigationBar поверх остальных элементов
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

//@Composable
//fun MainScreen(onBackClick: () -> Unit, onSettingsClick: () -> Unit, onNotificationClick: () -> Unit, onBankClick: () -> Unit) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Black)
//    ) {
//        // Status Bar
//        StatusBar(
//            info = "Your expenses",
//            onBackClick = {
//            onBackClick()
//        })
//
//        Spacer(modifier = Modifier.weight(1f))
//
//        // Your main content goes here (e.g., expenses list)
//
//        // Bottom Navigation Bar
//        BottomNavigationBar1(
//            onItemSelected = { selectedItem ->
//                // Handle generic item selection if needed
//            },
//            onSettingsClick = {
//                onSettingsClick()
//            },
//            onNotificationClick = {
//                onNotificationClick()
//            },
//            onBankClick = {
//                onBankClick()
//            }
//        )
//    }
//}



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

            IconButton(
                modifier = Modifier
                    .size(45.dp) // Adjust size to match the rounded background
                    .background(
                        if (isSelected) Color(0xFFFCF485) else Color.Black, // Highlight the selected item
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                onClick = {
                        when (label) {
                            "Notifications" -> onNotificationClick()
                            "Bank" -> onBankClick()
                            "Settings" -> onSettingsClick()
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


@Composable
fun ExpenseList(transactions: List<Transaction>) {
    LazyColumn {
        items(transactions) { transaction ->
            ExpenseItem(transaction)
        }
    }
}

@Composable
fun ExpenseItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(transaction.category, color = Color.White)
        Text("\$${transaction.amount}", color = Color.White)
        // Assuming transaction.color is an ARGB color value
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(Color(transaction.color))
        )
    }
}

