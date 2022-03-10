package com.app.mybank.ui.home.service

import com.app.mybank.core.usecase.BaseDataSource
import com.app.mybank.core.usecase.Resource
import com.app.mybank.ui.usecase.AppResource

interface HomeRepo {
    suspend fun getBalance(): Resource<BalanceResponse>

    suspend fun getTxnHistory(): Resource<HistoryResponse>
}

class HomeRepoImpl(
    appResource: AppResource,
    private val api: HomeApi
) : HomeRepo, BaseDataSource(appResource) {

    override suspend fun getBalance(): Resource<BalanceResponse> = getResult { api.getBalance() }

    override suspend fun getTxnHistory(): Resource<HistoryResponse> =
        getResult { api.getTransactions() }

}