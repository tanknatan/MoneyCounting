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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.my.moneycounting.R
import kotlin.math.pow








@Composable
fun CalculatorScreen(
    onBackClick: () -> Unit,
    onCalculateClick: (Double, Int, Double, Boolean) -> Unit,
    onSettingsClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onReportClick: () -> Unit
) {
    var creditAmount by remember { mutableStateOf(TextFieldValue("")) }
    var creditTerm by remember { mutableStateOf(TextFieldValue("")) }
    var interestRate by remember { mutableStateOf(TextFieldValue("")) }
    var isAnnuity by remember { mutableStateOf(false) }
    var monthlyPayment by remember { mutableStateOf("0.00") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Background Image positioned at the top but behind all other elements
        Image(
            painter = painterResource(id = R.drawable.bg2), // Replace with the correct image resource ID
            contentDescription = "Background Curve",
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
                .align(Alignment.TopCenter)
                .offset(y = (-50).dp) // Shift the image upwards by 50dp
        )

        // Scrollable content placed above the background image
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Status Bar at the top
            StatusBar(
                info = "Loan calculator",

                onBackClick = onBackClick)

            Spacer(modifier = Modifier.height(10.dp))

            // Monthly payment information
            Box(
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .height(150.dp)
                    .width(200.dp)
                    .background(Color(0xFFFCF485), shape = RoundedCornerShape(32.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Monthly payment:",
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "$ $monthlyPayment",
                        color = Color.Black,
                        fontSize = 32.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Credit Amount Input with $ prefix
            LoanCalculatorTextField(
                label = "Amount of credit:",
                value = creditAmount,
                onValueChange = { creditAmount = it },
                suffix = "$"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Credit Term Input
            LoanCalculatorTextField(
                label = "Credit term",
                value = creditTerm,
                onValueChange = { creditTerm = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Interest Rate Input with % suffix
            LoanCalculatorTextField(
                label = "Interest rate",
                value = interestRate,
                onValueChange = { interestRate = it },
                suffix = "%"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Annuity Checkbox
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Checkbox(
                    checked = isAnnuity,
                    onCheckedChange = { isAnnuity = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFFFCF485),
                        uncheckedColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Type of monthly payments: annuity",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }

            // Calculate Button
            Button(
                onClick = {
                    val amount = creditAmount.text.replace(",", "").toDoubleOrNull() ?: 0.0
                    val term = creditTerm.text.replace(",", "").toIntOrNull() ?: 0
                    val rate = (interestRate.text.replace(",", "").toDoubleOrNull() ?: 0.0) / 100 / 12

                    // Perform the loan calculation here
                    val payment = if (isAnnuity) {
                        calculateAnnuityPayment(amount, rate, term)
                    } else {
                        calculateLinearPayment(amount, rate, term)
                    }

                    // Update the monthly payment
                    monthlyPayment = String.format("%.2f", payment)
                    onCalculateClick(amount, term, rate, isAnnuity)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFCF485)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "Calculate",
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Bottom Navigation Bar
        BottomNavigationBar2(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            onItemSelected = { selectedItem ->
                // Handle generic item selection if needed
            },
            onSettingsClick = {
                onSettingsClick()
            },
            onNotificationClick = {
                onNotificationClick()
            },
            onReportClick = {
                onReportClick()
            }
        )
    }
}

@Composable
fun BottomNavigationBar2(
    modifier: Modifier = Modifier,
    onItemSelected: (String) -> Unit,
    onSettingsClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onReportClick: () -> Unit
) {
    Row(
        modifier = modifier
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
            Pair(R.drawable.ic_bank_act, "Bank"),
            Pair(R.drawable.ic_notification, "Notifications"),
            Pair(R.drawable.ic_settings, "Settings")
        )

        items.forEach { (imageRes, label) ->

            // Check if it's the selected item to highlight it
            val isSelected = label == "Bank" // Example: Highlight the "Report" item

            IconButton(
                modifier = Modifier
                    .size(45.dp)
                    .background(
                        if (isSelected) Color(0xFFFCF485) else Color.Black,
                        shape = CircleShape
                    ) ,
                onClick = {
                        when (label) {
                            "Report" -> onReportClick()
                            "Notifications" -> onNotificationClick()
                            "Settings" -> onSettingsClick()
                            else -> onItemSelected(label)
                        }
                    },
            ) {
                Icon(
                    painter = painterResource(id = imageRes),
                    contentDescription = label,
                    modifier = Modifier.size(33.dp),
                    tint = Color.Unspecified
                )
            }
        }
    }
}






@Composable
fun LoanCalculatorTextField(
    label: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    prefix: String = "",
    suffix: String = ""
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        BasicTextField(
            value = value.copy(
                text = "$prefix${value.text}$suffix"
            ),
            onValueChange = {
                // Ensure only the number part is editable
                val newText = it.text.removeSuffix(suffix).removePrefix(prefix)
                // Update the text field with the new value without adding prefix and suffix
                onValueChange(it.copy(text = newText))
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
            cursorBrush = androidx.compose.ui.graphics.SolidColor(Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(8.dp),
            singleLine = true
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFFCF485))
        )
    }
}


// Function to calculate annuity (fixed) payment
fun calculateAnnuityPayment(amount: Double, rate: Double, term: Int): Double {
    return if (rate == 0.0) {
        amount / term
    } else {
        amount * (rate * (1 + rate).pow(term)) / ((1 + rate).pow(term) - 1)
    }
}

// Function to calculate linear (decreasing) payment
fun calculateLinearPayment(amount: Double, rate: Double, term: Int): Double {
    return if (rate == 0.0) {
        amount / term
    } else {
        (amount / term) + (amount * rate)
    }
}
