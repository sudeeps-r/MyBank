package com.app.mybank.ui.login.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("/login")
    suspend fun doLogin(
        @Body request: LoginRequest
    ): Response<LoginResponse>

}