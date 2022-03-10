package com.app.mybank.util

import com.app.mybank.core.usecase.Resource
import com.app.mybank.core.util.CoreConstant
import com.app.mybank.ui.home.service.BalanceResponse
import com.app.mybank.ui.home.service.HistoryData
import com.app.mybank.ui.home.service.HistoryFromAccountData
import com.app.mybank.ui.home.service.HistoryResponse
import com.app.mybank.ui.login.service.LoginResponse
import com.app.mybank.ui.transfer.service.PayeeData
import com.app.mybank.ui.transfer.service.PayeeList
import com.app.mybank.ui.transfer.service.TransferData
import com.app.mybank.ui.transfer.service.TransferResponse
import com.app.mybank.ui.util.ViewConstant

object MockDataStore {

    val mockErrorMsg = "Error occurred"
    val mockBalance = "1000.00"
    val mockDate = "2021-10-11"

    val balanceResponse = BalanceResponse(
        status = CoreConstant.API_SUCCESS,
        balance = mockBalance.toDouble(),
        accountNo = "123-456-789"
    )

    val mockWrappedBalanceResponse = Resource.success(balanceResponse)

    private val historyData = HistoryData(
        id = "123",
        type = ViewConstant.IS_RECIEVED,
        amount = mockBalance.toDouble(),
        currency = "SGD",
        desc = null,
        date = mockDate,
        from = HistoryFromAccountData(
            accntNo = "123-456-789",
            name = "Jake"
        ),
        receiver = HistoryFromAccountData(
            accntNo = "123-456-789",
            name = "Jake"
        )
    )

    val historyList = HistoryResponse(
        status = "success",
        response = listOf(historyData)
    )

    val mockHistory = Resource.success(historyList)

    private val payeeData = PayeeData(
        id = "id",
        name = "name",
        accountNo = "123-456-789"
    )

    val payeeList = PayeeList(
        status = "success",
        list = listOf(payeeData)
    )

    val mockPayeeList = Resource.success(payeeList)

    val transferResponse = TransferResponse(
        status = "success",
        data = TransferData(
            id = "123",
            recipientAccountNo = "123-456",
            date = "2022-12-12",
            amount = 100.00
        )
    )

    val mockTransferResponse = Resource.success(transferResponse)

    val loginResponse = LoginResponse(
        status = "success",
        token = "token",
        username = "user"
    )

    val mockLoginResponse = Resource.success(loginResponse)

    inline fun <reified T> getError(): Resource<T> {
        return Resource.error(mockErrorMsg)
    }
}