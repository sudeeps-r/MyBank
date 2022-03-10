package com.app.mybank.ui.home

import com.app.mybank.R
import com.app.mybank.core.usecase.Resource
import com.app.mybank.ui.home.service.HomeApi
import com.app.mybank.ui.home.service.HomeRepo
import com.app.mybank.ui.home.service.HomeRepoImpl
import com.app.mybank.ui.usecase.AppResource
import com.app.mybank.util.MockDataStore
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class HomeRepoTest {

    private val appResource: AppResource = mock()
    private val api: HomeApi = mock()
    private lateinit var repo: HomeRepo

    @Before
    fun setUp() {
        repo = HomeRepoImpl(
            appResource = appResource,
            api = api
        )
    }

    @Test
    fun `test fetch balance`() = runBlocking {
        val response = Response.success(MockDataStore.balanceResponse)
        whenever(api.getBalance()).thenReturn(response)
        val result = repo.getBalance()
        Assert.assertEquals(MockDataStore.balanceResponse, result.data)
        Assert.assertEquals(Resource.Status.SUCCESS, result.status)
    }

    @Test
    fun `test API failure`() = runBlocking {
        val noInternet = "No Internet"
        given(api.getBalance()).willAnswer { throw IOException() }
        whenever(
            appResource.getString(R.string.no_internet_connection)
        ).thenReturn(noInternet)
        val result = repo.getBalance()
        Assert.assertEquals(noInternet, result.message)
        Assert.assertEquals(Resource.Status.ERROR, result.status)
    }

    @Test
    fun `test fetch txn history`() = runBlocking {
        val response = Response.success(MockDataStore.historyList)
        whenever(api.getTransactions()).thenReturn(response)
        val result = repo.getTxnHistory()
        Assert.assertEquals(MockDataStore.historyList, result.data)
        Assert.assertEquals(Resource.Status.SUCCESS, result.status)
    }

}