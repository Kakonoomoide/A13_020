package com.example.eventorganizer.service_api

import com.example.eventorganizer.model.AllTransactionResponse
import com.example.eventorganizer.model.TransactionDetailResponse
import com.example.eventorganizer.model.Transactions
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface TransactionsService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // post data
    @POST("add")
    suspend fun insertTransactions(@Body transactions: Transactions)

    // get data
    @GET(".")
    suspend fun getAllTransactions(): AllTransactionResponse

    // get data by id
    @GET("{id_transaksi}")
    suspend fun getTransactionsById(@Path("id_transaksi") IdTransactions: Int): TransactionDetailResponse
    

    // delete data by id
    @DELETE("{id_transaksi}")
    suspend fun deleteTransactions(@Path("id_transaksi") IdTransactions: Int): Response<Void>
}