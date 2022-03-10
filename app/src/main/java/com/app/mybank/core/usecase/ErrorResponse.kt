package com.app.mybank.core.usecase

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("status") val status: String,
    @SerializedName("error") val error: String
)
