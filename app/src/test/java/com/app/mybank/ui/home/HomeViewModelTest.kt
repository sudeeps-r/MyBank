package com.app.mybank.ui.home

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.mybank.R
import com.app.mybank.core.dao.DataStore
import com.app.mybank.core.util.CurrencyUtil
import com.app.mybank.core.util.DateUtil
import com.app.mybank.ui.home.service.BalanceResponse
import com.app.mybank.ui.home.service.HistoryViewResponse
import com.app.mybank.ui.home.service.HomeRepo
import com.app.mybank.ui.usecase.AppResource
import com.app.mybank.util.MockDataStore
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    private val repo: HomeRepo = mock()
    private val dataStore: DataStore = mock()
    private val appResource: AppResource = mock()
    private val currencyUtil: CurrencyUtil = mock()
    private val dateUtil: DateUtil = mock()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel(
            repo,
            dataStore,
            appResource,
            currencyUtil,
            dateUtil
        )
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `test fetch balance`() = runBlocking {
        val response = MockDataStore.mockWrappedBalanceResponse
        val balance = MockDataStore.mockBalance
        whenever(
            appResource.getString(R.string.label_currency, balance)
        ).thenReturn(balance)
        whenever(
            currencyUtil.format(balance.toDouble())
        ).thenReturn(balance)
        whenever(repo.getBalance()).thenReturn(response)
        viewModel.refreshBalance()
        viewModel.balance.observeForever {
            Assert.assertEquals(response, it?.data)
            Assert.assertEquals(View.GONE, viewModel.showError.get())
        }
    }

    @Test
    fun `test fetch balance failure`() = runBlocking {
        val response = MockDataStore.getError<BalanceResponse>()
        whenever(repo.getBalance()).thenReturn(response)
        viewModel.refreshBalance()
        viewModel.balance.observeForever {
            Assert.assertEquals(MockDataStore.mockErrorMsg, it?.message)
        }
    }

    @Test
    fun `test fetch txn history`() = runBlocking {
        val response = MockDataStore.mockHistory
        val balance = MockDataStore.mockBalance
        whenever(
            dateUtil.format(MockDataStore.mockDate)
        ).thenReturn(MockDataStore.mockDate)
        whenever(appResource.getString(R.string.label_currency, balance)).thenReturn(balance)
        whenever(
            currencyUtil.format(balance.toDouble())
        ).thenReturn(balance)
        whenever(repo.getTxnHistory()).thenReturn(response)
        val mockList = listOf(
            HistoryViewResponse(
                date = MockDataStore.mockDate,
                isReceived = true,
                currency = "SGD",
                response = response.data?.response!!.first()
            )
        )
        val mockResponse = Pair(
            listOf(MockDataStore.mockDate),
            mapOf(MockDataStore.mockDate to mockList)
        )
        viewModel.refreshHistory()
        viewModel.txnHistory.observeForever {
            Assert.assertEquals(mockResponse, it?.data)
            Assert.assertEquals(View.GONE, viewModel.showError.get())
        }
    }


}