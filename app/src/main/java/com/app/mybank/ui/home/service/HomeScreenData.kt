package com.app.mybank.ui.home.service

import com.google.gson.annotations.SerializedName

data class BalanceResponse(
    @SerializedName("status") val status: String,
    @SerializedName("balance") val balance: Double,
    @SerializedName("accountNo") val accountNo: String
)

data class HistoryResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val response: List<HistoryData>
)

data class HistoryData(
    @SerializedName("id") val id: String,
    @SerializedName("transactionType") val type: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("currency") val currency: String,
    @SerializedName("description") val desc: String?,
    @SerializedName("transactionDate") val date: String,
    @SerializedName("sender") val from: HistoryFromAccountData,
    @SerializedName("receipient") val receiver: HistoryFromAccountData
)

data class HistoryFromAccountData(
    @SerializedName("accountNo") val accntNo: String,
    @SerializedName("accountHolder") val name: String
)

data class HistoryViewResponse(
    val date: String,
    val currency: String,
    val isReceived: Boolean,
    val response: HistoryData
)