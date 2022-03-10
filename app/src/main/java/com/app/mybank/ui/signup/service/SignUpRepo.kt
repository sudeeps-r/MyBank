package com.app.mybank.ui.signup.service

import com.app.mybank.core.usecase.BaseDataSource
import com.app.mybank.core.usecase.Resource
import com.app.mybank.ui.login.service.LoginResponse
import com.app.mybank.ui.usecase.AppResource

interface SignUpRepo {
    suspend fun doRegister(
        request: RegisterRequest
    ): Resource<LoginResponse>
}

class SignUpRepoImpl(
    appResource: AppResource,
    private val api: SignUpApi
) : SignUpRepo,
    BaseDataSource(appResource) {

    override suspend fun doRegister(
        request: RegisterRequest
    ): Resource<LoginResponse> {
        val response = getResult {
            api.doRegister(request)
        }
        return response
    }
}