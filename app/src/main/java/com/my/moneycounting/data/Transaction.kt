package com.my.moneycounting.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Date
import java.util.Locale

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
val Double.formattedAmount: String
    get() {
        val dec = DecimalFormat(
            "###,###,###,###,###.00",
            DecimalFormatSymbols(Locale.ENGLISH)
        )

        val amount = dec.format(this)
            .replace(",", " ")
            .replace(".00", "")
        return "$ $amount"
    }

