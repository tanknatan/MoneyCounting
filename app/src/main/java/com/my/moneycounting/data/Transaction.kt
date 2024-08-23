package com.my.moneycounting.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transaction_table")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String, // "Income" or "Expense"
    val category: String, // Type of income or expense (e.g., "Food", "Salary")
    val amount: Double, // Amount of the transaction
    val date: Date, // Date of the transaction
    val iconDrawable : Int,
    val color: Int
)

