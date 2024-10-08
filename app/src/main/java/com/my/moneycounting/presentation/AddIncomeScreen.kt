package com.my.moneycounting.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.my.moneycounting.R
import com.my.moneycounting.data.Transaction
import java.util.Date
import kotlin.random.Random

@Composable
fun AddIncomeScreen(
    viewModel: TransactionViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var amount by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(black)
    ) {
        StatusBar(
            info = "Add Income",
            onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(24.dp))

        // Поле для ввода суммы
        AmountInputField(value = amount) { amount = it }

        Spacer(modifier = Modifier.height(24.dp))

        // Заголовок для выбора категории
        Text(
            text = "Select a category",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопки выбора категории
        CategorySelectionButtons(selectedCategory) { category ->
            selectedCategory = category
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Кнопка для добавления операции
        Button(
            onClick = {
                if (selectedCategory == null) {
                    // Показать сообщение об ошибке
                    return@Button
                }
                if (amount.isEmpty() || amount.toDoubleOrNull() == null){
                    //
                    return@Button
                } else {
                    val transaction = Transaction(
                        type = "Income",
                        date = Date(),
                        amount = amount.toDouble(),
                        iconDrawable = selectedCategory!!.icon,
                        category = selectedCategory!!.name,
                        color = Random.nextColor()
                    )

                    viewModel.addTransaction(transaction)
                    onBackClick()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = yellow),
            shape = RoundedCornerShape(50.dp) // Закругленная форма кнопки
        ) {
            Text("Add operation", color = black, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
fun CategorySelectionButtons(selectedCategory: Category?, onCategorySelected: (Category) -> Unit) {
    val categories = listOf(
        Category("Trading", R.drawable.ic_trading),
        Category("Crypto", R.drawable.ic_crypto),
        Category("Home", R.drawable.ic_home),
        Category("Transfers", R.drawable.ic_transfers),
        Category("Sales", R.drawable.ic_sales),
        Category("Others", R.drawable.ic_others)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        categories.chunked(3).forEachIndexed { rowIndex, rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowItems.forEachIndexed { index, category ->
                    val isSelected = selectedCategory == category
                    Box(
                        modifier = Modifier
                            .size(width = 100.dp, height = 80.dp)
                            .background(Color(0xFF282A2C), shape = RoundedCornerShape(12.dp))
                            .border(
                                width = 2.dp,
                                color = if (isSelected) yellow else Color.Transparent,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable { onCategorySelected(category) }
                    ) {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp) // Размер желтого круга
                                    .background(yellow, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = category.icon),
                                    contentDescription = category.name,
                                    tint = black, // Черная иконка на желтом круге
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = category.name, color = Color.White, fontSize = 12.sp)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmountInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Enter the amount", color = Color.White) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .then(modifier),
        shape = CircleShape, // Закругленные края
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = yellow,
            unfocusedBorderColor = yellow,
            focusedTextColor = Color.White,
            containerColor = black
        ),
        singleLine = true,
        textStyle = TextStyle(color = Color.White),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
}


@Preview
@Composable
fun AddIncomeScreenPreview() {
    AddIncomeScreen(onBackClick = {})
}

data class Category(val name: String, val icon: Int)