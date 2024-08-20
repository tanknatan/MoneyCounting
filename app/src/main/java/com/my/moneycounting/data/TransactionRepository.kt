package com.my.moneycounting.data

import kotlinx.coroutines.flow.Flow

class TransactionRepository(private val transactionDao: TransactionDao) {

    val allTransactions: Flow<List<Transaction>> = transactionDao.getAllTransactions()

    fun getTransactionsByType(type: String): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByType(type)
    }

    suspend fun insert(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }

    suspend fun deleteTransaction(id: Int) {
        transactionDao.deleteTransactionById(id)
    }
}
