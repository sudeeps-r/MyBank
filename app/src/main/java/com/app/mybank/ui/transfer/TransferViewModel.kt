package com.app.mybank.ui.transfer

import android.text.Editable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.app.mybank.ui.base.BaseViewModel
import com.app.mybank.ui.transfer.service.TransferRepo
import com.app.mybank.ui.transfer.service.TransferRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    private val repo: TransferRepo
) : BaseViewModel() {

    private val transfer = MutableLiveData<TransferRequest>()
    val accnt = ObservableField<String>()
    val amnt = ObservableField<String>()
    val desc = ObservableField<String>()

    val didTransfer = transfer.switchMap { request ->

        makeCall(
            networkCall = {
                showLoader()
                repo.doTransfer(request)
            },
            saveCallResult = {
                showResult()
            },
            errorCallback = {
                showResult()
            }
        )
    }
    val payeeList = makeCall(
        networkCall = {
            showLoader()
            repo.getPayeeList()
        },
        saveCallResult = {
            showResult()
        },
        errorCallback = {
            showError(it.message)
        }
    )

    fun setAccountNo(s: Editable) {
        accnt.set(s.toString())
    }

    fun setDesc(s: Editable) {
        desc.set(s.toString())
    }

    fun setAmount(s: Editable) {
        amnt.set(
            s.toString()
        )
    }

    fun doTransfer() {
        val request = TransferRequest(
            accountNo = accnt.get().orEmpty(),
            amount = (amnt.get() ?: "0.0").toDouble(),
            desc = desc.get()
        )
        transfer.value = request
    }
}