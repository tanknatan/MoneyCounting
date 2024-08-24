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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
import com.my.moneycounting.data.formattedAmount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

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

    fun setDate(date: String) {
        viewModelScope.launch {
            _selectedDate.value = date
        }
    }

    private val dao: TransactionDao = AppDatabase.getDatabase(application).transactionDao()
    private val _typeTransactions: MutableStateFlow<String> = MutableStateFlow("Income")
    val typeTransactions = _typeTransactions.asStateFlow()
    private val _selectedDate: MutableStateFlow<String> = MutableStateFlow("Day")
    val selectedDate = _selectedDate.asStateFlow()
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
        .combine(_selectedDate) { transactions, date ->
            transactions.filter {transaction ->
                when (date) {
                    "Day" -> {
                        val calendar = Calendar.getInstance().apply { time = Date() }
                        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
                        val transactionDay = calendar.apply { time = transaction.date }.get(Calendar.DAY_OF_MONTH)
                        transactionDay == currentDay
                    }
                    "Week" -> {
                        val calendar = Calendar.getInstance().apply {
                            time = Date()
                            firstDayOfWeek = Calendar.MONDAY
                        }
                        val currentWeek = calendar.get(Calendar.WEEK_OF_YEAR)
                        val transactionWeek = calendar.apply {
                            time = transaction.date
                            firstDayOfWeek = Calendar.MONDAY
                        }.get(Calendar.WEEK_OF_YEAR)
                        transactionWeek == currentWeek
                    }
                    "Month" -> {
                        val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
                        val transactionMonth = Calendar.getInstance().apply { time = transaction.date }.get(Calendar.MONTH)
                        transactionMonth == currentMonth
                    }
                    "Year" -> {
                        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                        val transactionYear = Calendar.getInstance().apply { time = transaction.date }.get(Calendar.YEAR)
                        transactionYear == currentYear
                    }
                    else -> throw IllegalArgumentException("Unknown date: $date")
                }
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
    onBankClick: () -> Unit
) {
    val transactions by viewModel.transactions.collectAsState(initial = emptyList())
    val selectedOperationType by viewModel.typeTransactions.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
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
                    onBackClick = {},
                    isFirst = true
                    )
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
                        backgroundColor = black,
                        showSliceLabels = true,
                        sliceLabelEllipsizeAt = TextUtils.TruncateAt.END
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        DonutPieChart(
                            modifier = Modifier
                                .fillMaxSize(),
                            pieChartData = donutChartData,
                            pieChartConfig = donutChartConfig
                        )
                        Text(
                            text = transactions.sumOf { it.amount }.formattedAmount,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
                else {
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
                    selectedPeriod = selectedDate,
                    onPeriodSelected = viewModel::setDate
                )
            Spacer(modifier = Modifier.height(16.dp))
                IncomeExpenseToggle(
                    selectedOption = selectedOperationType,
                    onOptionSelected = viewModel::setType
                )
                Spacer(modifier = Modifier.height(16.dp))
                fun onAdd(): () -> Unit = {
                    onAddTransaction(selectedOperationType)
                }
                AddOperationButton(onAdd())
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Your ${selectedOperationType.lowercase()}:",
                    color = Color(0xffC0C0C0),
                )
                ExpenseList(transactions)
            }
            BottomNavigationBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                selectedItem = "Report",
                onSettingsClick = onSettingsClick,
                onReportClick = {

                },
                onBankClick = onBankClick,
                onNotificationClick = onNotificationClick
            )

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
            fontWeight = FontWeight.Black
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
                    fontWeight = FontWeight.Black
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
                    fontWeight = FontWeight.Black
                )
            }
        }
    }
}


@Composable
fun ExpenseList(transactions: List<Transaction>) {
    LazyColumn(
        modifier = Modifier
            .padding(bottom = 95.dp)
    ) {
        items(transactions) { transaction ->
            ExpenseItem(transaction, transaction.calcPercent(transactions))
        }
    }
}

private fun Transaction.calcPercent(transactions: List<Transaction>): Number {
    val totalAmount = transactions.sumOf { it.amount }
    val percent = amount.toFloat() / totalAmount * 100f
    val roundedPercent = percent.roundToInt()
    return if (roundedPercent == 0) percent else percent.roundToInt()
}

@Composable
fun ExpenseItem(transaction: Transaction, percent: Number) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 4.dp)
            .fillMaxWidth()
            .background(Color(0xFF292929), shape = CircleShape)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(yellow, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = transaction.iconDrawable), // замени на ImageVector или Painter для иконки
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = transaction.category,
                color = Color.White,
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Black
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = transaction.amount.formattedAmount,
                color = Color.White,
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${if (percent is Double){
                    String.format(Locale.getDefault(),"%.2f", percent)
                } else percent}%",
                color = Color.White,
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Black
            )
        }
    }
}

