package com.example.eventorganizer.repository

import com.example.eventorganizer.model.AllTransactionResponse
import com.example.eventorganizer.model.Transactions
import com.example.eventorganizer.service_api.TransactionsService
import okio.IOException

interface TransactionsRepository {
    suspend fun insertTransactions(transactions: Transactions)

    suspend fun getAllTransactions(): AllTransactionResponse

    suspend fun deleteTransactions(IdTransactions: Int)

    suspend fun getTransactionsById(IdTransactions: Int): Transactions
}

class TransactionsApiHandler(
    private val transactionApiHandler: TransactionsService
): TransactionsRepository {
    override suspend fun insertTransactions(transactions: Transactions) {
        transactionApiHandler.insertTransactions(transactions)
    }

    override suspend fun getAllTransactions(): AllTransactionResponse {
        return transactionApiHandler.getAllTransactions()
    }

    override suspend fun deleteTransactions(IdTransactions: Int) {
        try {
            val response = transactionApiHandler.deleteTransactions(IdTransactions)
            if (!response.isSuccessful){
                throw IOException("Failed to delete Transactions. HTTP Status code:" + "${(response.code())}")
            }else{
                response.message()
                println(response.message())
            }
        }
        catch (e: Exception){
            throw e
        }
    }

    override suspend fun getTransactionsById(IdTransactions: Int): Transactions {
        return transactionApiHandler.getTransactionsById(IdTransactions).data
    }
}