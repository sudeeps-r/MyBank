package com.app.mybank.ui.home

import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.app.mybank.R
import com.app.mybank.core.dao.DataStore
import com.app.mybank.core.usecase.Resource
import com.app.mybank.core.util.CurrencyUtil
import com.app.mybank.core.util.DateUtil
import com.app.mybank.core.util.PreferenceKey
import com.app.mybank.ui.base.BaseViewModel
import com.app.mybank.ui.home.service.HistoryViewResponse
import com.app.mybank.ui.home.service.HomeRepo
import com.app.mybank.ui.usecase.AppResource
import com.app.mybank.ui.util.ViewConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: HomeRepo,
    private val dataStore: DataStore,
    private val appResource: AppResource,
    private val currencyUtil: CurrencyUtil,
    private val dateUtil: DateUtil
) : BaseViewModel() {

    private val fetchBalance = MutableLiveData<Boolean>()
    private val fetchTxnHistory = MutableLiveData<Boolean>()
    val accountNo = ObservableField<String>()
    val accountHolder = ObservableField<String>()
    val balanceText = ObservableField<String>()
    val balance = fetchBalance.switchMap {
        makeCall(
            networkCall = {
                showLoader()
                repo.getBalance()
            },
            saveCallResult = {
                showResult()
                viewModelScope.launch {
                    balanceText.set(
                        appResource.getString(
                            R.string.label_currency,
                            currencyUtil.format(it.balance)
                        )
                    )
                    accountNo.set(it.accountNo)
                    accountHolder.set(dataStore.getString(PreferenceKey.USER_NAME))
                }
            },
            errorCallback = {
                showError(it.message)
            }
        )
    }

    val txnHistory = fetchTxnHistory.switchMap {
        makeCall(
            networkCall = {
                val nwResponse = repo.getTxnHistory()
                val response = nwResponse.data
                val formatted = response?.response?.map {
                    HistoryViewResponse(
                        date = dateUtil.format(it.date),
                        currency = appResource.getString(
                            R.string.label_currency,
                            currencyUtil.format(it.amount)
                        ),
                        response = it,
                        isReceived = ViewConstant.IS_RECIEVED == it.type
                    )
                }
                val map = formatted?.groupBy { it.date }
                Resource(
                    status = nwResponse.status,
                    code = nwResponse.code,
                    message = nwResponse.message,
                    data = Pair(map?.keys?.toList(), map)
                )
            }
        ) {}
    }

    val logout = makeCall(
        networkCall = {
            dataStore.clear()
            Resource.success(true)
        },
        {}
    )

    @VisibleForTesting
    fun refreshBalance() {
        fetchBalance.value = true
    }

    @VisibleForTesting
    fun refreshHistory() {
        fetchTxnHistory.value = true
    }

    fun refreshHome() {
        refreshBalance()
        refreshHistory()
    }
}