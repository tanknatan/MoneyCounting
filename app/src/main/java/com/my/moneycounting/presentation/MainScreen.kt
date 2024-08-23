package com.my.moneycounting.presentation

import android.app.Application
import android.text.TextUtils
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.my.moneycounting.data.AppDatabase
import com.my.moneycounting.data.Transaction
import com.my.moneycounting.data.TransactionDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

val yellow = Color(0xFFFCF485)
val gray = Color(0xff282A2C)
val black = Color(0xff151718)

class TransactionViewModel(application: Application) : AndroidViewModel(application) {
    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            dao.insertTransaction(transaction)
        }
    }

    fun setType(type: String) {
        viewModelScope.launch {
            _typeTransactions.value = type
        }
    }

    private val dao: TransactionDao = AppDatabase.getDatabase(application).transactionDao()
    private val _typeTransactions: MutableStateFlow<String> = MutableStateFlow("Income")
    val typeTransactions = _typeTransactions.asStateFlow()
    val transactions: Flow<List<Transaction>> = dao
        .getAllTransactions()
        .combine(_typeTransactions) { transactions, type ->
            if (type == "Income") {
                transactions.filter { it.type == "Income" }
            }
            else {
                transactions.filter { it.type == "Expenses" }
            }
        }
    // You can also add functions to add new transactions, delete them, etc.
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    viewModel: TransactionViewModel = viewModel(),
    onAddTransaction: (String) -> Unit,
    onSettingsClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onBankClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val transactions by viewModel.transactions.collectAsState(initial = emptyList())
    val selectedOperationType by viewModel.typeTransactions.collectAsState()
    var selectedDate by remember { mutableStateOf("Day") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StatusBar(info = "Your ${selectedOperationType.lowercase()}",
                onBackClick = {
                    onBackClick()
                })
            Spacer(modifier = Modifier.height(16.dp))
            if (transactions.isNotEmpty()) {
                val donutChartData = PieChartData(
                    slices = transactions.map {
                        PieChartData.Slice(
                            label = it.category,
                            value = ((it.amount.toFloat() / transactions.sumOf { transaction -> transaction.amount }) * 100).also {
                                Log.d("TAG", "donutChartData: $it")
                            }.toFloat(),
                            color = Color(it.color)
                        )
                    },
                    plotType = PlotType.Pie
                )
                val donutChartConfig = PieChartConfig(
                    labelVisible = true,
                    labelFontSize = 16.sp,
                    strokeWidth = 6f,
                    labelColor = Color.White,
                    activeSliceAlpha = 0.9f,
                    isAnimationEnable = true,
                    backgroundColor = gray,
                    showSliceLabels = true,
                    sliceLabelEllipsizeAt = TextUtils.TruncateAt.END
                )
                DonutPieChart(
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.CenterHorizontally),
                    pieChartData = donutChartData,
                    pieChartConfig = donutChartConfig
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(
                            color = yellow,
                            shape = CircleShape
                        )
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No\n${selectedOperationType.lowercase()}\nfound",
                        color = black,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            PeriodSelectionButtons(
                selectedPeriod = selectedDate
            ) {
                selectedDate = it
            }
            Spacer(modifier = Modifier.height(16.dp))
            IncomeExpenseToggle(
                selectedOption = selectedOperationType,
                onOptionSelected = viewModel::setType
            )
            Spacer(modifier = Modifier.height(16.dp))
            AddOperationButton {
                onAddTransaction(selectedOperationType)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Your ${selectedOperationType.lowercase()}:",
                color = Color(0xffC0C0C0),
            )
            ExpenseList(transactions)
            Spacer(modifier = Modifier.weight(1f))
            BottomNavigationBar(
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
                },
                selectedItem = "Report",
                onReportClick = {

                }
            )
        }

        // BottomNavigationBar поверх остальных элементов
    }
}

@Composable
fun AddOperationButton(onAddClick: () -> Unit) {
    Button(
        onClick = onAddClick,
        modifier = Modifier
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = yellow, contentColor = black)
    ) {
        Text(
            text = "Add operation",
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun IncomeExpenseToggle(
    modifier: Modifier = Modifier,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    val options = listOf("Income", "Expenses")

    Row(
        modifier = Modifier
            .background(
                color = Color(0xFF282A2C),
                shape = ButtonDefaults.shape
            )
            .width(200.dp)
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        options.forEach { option ->
            Button(
                onClick = { onOptionSelected(option) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (option == selectedOption) yellow else Color.Transparent,
                    contentColor = if (option == selectedOption) black else Color.White
                ),
                modifier = Modifier
                    .weight(1f),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = option,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun PeriodSelectionButtons(
    modifier: Modifier = Modifier,
    selectedPeriod: String = "Day", // Можно передать начальное значение
    onPeriodSelected: (String) -> Unit // Функция для обработки выбора периода
) {
    val periods = listOf("Day", "Week", "Month", "Year")

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(
                color = Color(0xFF282A2C),
                shape = ButtonDefaults.shape
            )
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        periods.forEach { period ->
            Button(
                onClick = { onPeriodSelected(period) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (period == selectedPeriod) yellow else Color.Transparent,
                    contentColor = if (period == selectedPeriod) black else Color.White
                ),
                modifier = Modifier
                    .weight(1f),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = period,
                    fontWeight = FontWeight.Bold
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
                .background(Color(transaction.iconDrawable))
        )
    }
}

