package com.app.mybank.ui.transfer.service

import com.app.mybank.core.usecase.BaseDataSource
import com.app.mybank.core.usecase.Resource
import com.app.mybank.ui.usecase.AppResource

interface TransferRepo {
    suspend fun getPayeeList(
    ): Resource<PayeeList>

    suspend fun doTransfer(
        request: TransferRequest
    ): Resource<TransferResponse>
}

internal class TransferRepoImpl(
    appResource: AppResource,
    private val api: TransferApi
) : TransferRepo, BaseDataSource(appResource) {

    override suspend fun getPayeeList(
    ): Resource<PayeeList> = getResult {
        api.getPayeeList()
    }

    override suspend fun doTransfer(
        request: TransferRequest
    ): Resource<TransferResponse> = getResult {
        api.doTransfer(request)
    }
}