package com.app.mybank.ui.signup.service

import com.app.mybank.ui.login.service.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpApi {
    @POST("/register")
    suspend fun doRegister(
        @Body request: RegisterRequest
    ): Response<LoginResponse>
}