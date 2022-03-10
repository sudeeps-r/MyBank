package com.app.mybank.ui.login.service

import com.app.mybank.core.dao.DataStore
import com.app.mybank.core.usecase.BaseDataSource
import com.app.mybank.core.usecase.Resource
import com.app.mybank.ui.usecase.AppResource

interface LoginRepo {
    suspend fun doLogin(
        request: LoginRequest
    ): Resource<LoginResponse>
}

class LoginRepoImpl(
    appResource: AppResource,
    private val api: LoginApi,
    private val dataStore: DataStore
) : LoginRepo, BaseDataSource(appResource) {

    override suspend fun doLogin(
        request: LoginRequest
    ): Resource<LoginResponse> {
        val res = getResult {
            api.doLogin(request)
        }
        saveToken(dataStore, res)
        return res
    }

}