package com.my.moneycounting.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
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
            .background(black)
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
                .align(Alignment.TopCenter)
                .padding(bottom = 48.dp),
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
                    .padding(vertical = 12.dp)
                    .height(100.dp)
                    .width(150.dp)
                    .background(Color(0xFFFCF485), shape = RoundedCornerShape(32.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Monthly payment:",
                        color = black,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "$ $monthlyPayment",
                        color = black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

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

            Spacer(modifier = Modifier.height(8.dp))

            // Annuity Checkbox
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
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
                    color = black,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Bottom Navigation Bar
        BottomNavigationBar(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            selectedItem = "Bank",
            onSettingsClick = {
                onSettingsClick()
            },
            onReportClick = {
                onReportClick()
            },
            onBankClick = {

            },
            onNotificationClick = {
                onNotificationClick()
            }
        )
    }
}





@OptIn(ExperimentalMaterial3Api::class)
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
        OutlinedTextField(
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
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(8.dp),
            shape = CircleShape, // Закругленные края
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = yellow,
                unfocusedBorderColor = yellow,
                focusedTextColor = Color.White,
                containerColor = black
            ),
            singleLine = true
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
