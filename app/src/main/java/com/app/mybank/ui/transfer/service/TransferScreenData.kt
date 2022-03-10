package com.app.mybank.ui.transfer.service


import com.google.gson.annotations.SerializedName

data class PayeeData(
    @SerializedName("id") val id: String,
    @SerializedName("accountNo") val accountNo: String,
    @SerializedName("name") val name: String
) {
    override fun toString(): String = name
}

data class PayeeList(
    @SerializedName("status") val status: String,
    @SerializedName("data") val list: List<PayeeData>
)

data class TransferRequest(
    @SerializedName("receipientAccountNo") val accountNo: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("description") val desc: String?
)

data class TransferResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: TransferData
)

data class TransferData(
    @SerializedName("id") val id: String,
    @SerializedName("recipientAccountNo") val recipientAccountNo: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("date") val date: String
)