package com.app.mybank.ui.login.service

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status") val status: String,
    @SerializedName("token") val token: String,
    @SerializedName("username") val username: String
)

data class LoginRequest(
    @SerializedName("username") val userName: String,
    @SerializedName("password") val password: String
)