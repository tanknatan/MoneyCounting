package com.my.moneycounting.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface TransactionDao {

    @Insert
    suspend fun insertTransaction(transaction: Transaction)

    @Query("SELECT * FROM transaction_table ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Query("SELECT * FROM transaction_table WHERE type = :type ORDER BY date DESC")
    fun getTransactionsByType(type: String): Flow<List<Transaction>>

    @Query("DELETE FROM transaction_table WHERE id = :id")
    suspend fun deleteTransactionById(id: Int)
}
