package com.app.mybank.ui.home.service

import retrofit2.Response
import retrofit2.http.GET

interface HomeApi {

    @GET("/balance")
    suspend fun getBalance(): Response<BalanceResponse>

    @GET("/transactions")
    suspend fun getTransactions(): Response<HistoryResponse>
}