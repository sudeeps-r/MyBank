package com.app.mybank.ui.transfer.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TransferApi {

    @GET("/payees")
    suspend fun getPayeeList(): Response<PayeeList>

    @POST("/transfer")
    suspend fun doTransfer(
        @Body request: TransferRequest
    ): Response<TransferResponse>
}