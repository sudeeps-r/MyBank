package com.app.mybank.ui.signup.service

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("username") val userName: String,
    @SerializedName("password") val password: String
)

data class RegisterResponse(
    @SerializedName("status") val status: String
)