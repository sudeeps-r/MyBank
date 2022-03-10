package com.app.mybank.ui.transfer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.mybank.core.usecase.Resource
import com.app.mybank.ui.transfer.service.TransferRepo
import com.app.mybank.ui.transfer.service.TransferRequest
import com.app.mybank.util.MockDataStore
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TransferViewModelTest {

    private val repo: TransferRepo = mock()
    private lateinit var viewModel: TransferViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = TransferViewModel(repo)
    }

    @Test
    fun `test fetch payee list`() = runBlocking {
        val response = MockDataStore.mockPayeeList
        whenever(repo.getPayeeList()).thenReturn(response)
        viewModel.payeeList.observeForever {
            Assert.assertEquals(response, it.data)
            Assert.assertEquals(Resource.Status.SUCCESS, it.status)
        }
    }

    @Test
    fun `test transfer perform`() = runBlocking {
        val response = MockDataStore.mockTransferResponse
        viewModel.amnt.set("100")
        viewModel.accnt.set("123-456-789")
        viewModel.desc.set("hello")
        val request = TransferRequest(
            accountNo = viewModel.accnt.get().orEmpty(),
            amount = viewModel.amnt.get().orEmpty().toDouble(),
            desc = viewModel.desc.get().orEmpty()
        )
        whenever(repo.doTransfer(request)).thenReturn(response)
        viewModel.didTransfer.observeForever {
            Assert.assertEquals(response, it.data)
            Assert.assertEquals(Resource.Status.SUCCESS, it.status)
        }
    }

}