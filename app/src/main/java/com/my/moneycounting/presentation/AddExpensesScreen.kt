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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.my.moneycounting.R

@Composable
fun AddExpensesScreen(onBackClick: () -> Unit) {
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        StatusBar(
            info = "Add Expenses",
            onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(24.dp))

        // Поле для ввода суммы
        AmountInputField()

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
        CategorySelectionButtonsE(selectedCategory) { category ->
            selectedCategory = category
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Кнопка для добавления операции
        Button(
            onClick = { /* TODO: Add operation logic */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFCF485)),
            shape = RoundedCornerShape(50.dp) // Закругленная форма кнопки
        ) {
            Text("Add operation", color = Color.Black)
        }
    }
}

//@Composable
//fun AmountInputField() {
//    OutlinedTextField(
//        value = "",
//        onValueChange = {},
//        label = { Text("Enter the amount", color = Color.White) },
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp),
//        shape = RoundedCornerShape(50.dp), // Закругленные края
//        colors = TextFieldDefaults.outlinedTextFieldColors(
//            focusedBorderColor = Color.Yellow,
//            unfocusedBorderColor = Color.Yellow,
//            textColor = Color.White,
//            backgroundColor = Color.Black
//        ),
//        singleLine = true,
//        textStyle = TextStyle(color = Color.White)
//    )
//}

@Composable
fun CategorySelectionButtonsE(selectedCategory: String?, onCategorySelected: (String) -> Unit) {
    val categories = listOf(
        "Food", "Car", "Health",
        "Transport", "Pets", "Rest",
        "Fun", "Games", "Savings",
        "Presents", "Finance", "Rent",
        "Home", "Shopping", "Others"
    )

    val icons = listOf(
        R.drawable.ic_food,
        R.drawable.ic_car,
        R.drawable.ic_health,
        R.drawable.ic_transport,
        R.drawable.ic_pets,
        R.drawable.ic_rest,
        R.drawable.ic_fun,
        R.drawable.ic_games,
        R.drawable.ic_savings,
        R.drawable.ic_presents,
        R.drawable.ic_finance,
        R.drawable.ic_rent,
        R.drawable.ic_home,
        R.drawable.ic_shopping,
        R.drawable.ic_others
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
                                color = if (isSelected) Color(0xFFFCF485) else Color.Transparent,
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
                                    .size(40.dp) // Размер желтого круга
                                    .background(Color(0xFFFCF485), shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = icons[rowIndex * 3 + index]),
                                    contentDescription = category,
                                    tint = Color.Black, // Черная иконка на желтом круге
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = category, color = Color.White, fontSize = 12.sp)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}


@Preview
@Composable
fun AddExpensesScreenPreview() {
    AddExpensesScreen(onBackClick = {})
}
