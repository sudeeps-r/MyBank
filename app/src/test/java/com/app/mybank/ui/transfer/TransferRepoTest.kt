package com.app.mybank.ui.transfer

import com.app.mybank.ui.transfer.service.TransferApi
import com.app.mybank.ui.transfer.service.TransferRepo
import com.app.mybank.ui.transfer.service.TransferRepoImpl
import com.app.mybank.ui.transfer.service.TransferRequest
import com.app.mybank.ui.usecase.AppResource
import com.app.mybank.util.MockDataStore
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class TransferRepoTest {

    private val appResource: AppResource = mock()
    private val api: TransferApi = mock()
    private lateinit var repo: TransferRepo

    @Before
    fun setUp() {
        repo = TransferRepoImpl(
            appResource = appResource,
            api = api
        )
    }

    @Test
    fun `test fetch payee list`() = runBlocking {
        val response = Response.success(MockDataStore.payeeList)
        whenever(api.getPayeeList()).thenReturn(response)
        val result = repo.getPayeeList()
        Assert.assertEquals(MockDataStore.payeeList, result.data)
    }

    @Test
    fun `test fetch transfer perform`() = runBlocking {
        val request = TransferRequest(
            accountNo = "123",
            amount = 100.00,
            desc = null
        )
        val response = Response.success(MockDataStore.transferResponse)
        whenever(api.doTransfer(request)).thenReturn(response)
        val result = repo.doTransfer(request)
        Assert.assertEquals(MockDataStore.transferResponse, result.data)
    }
}