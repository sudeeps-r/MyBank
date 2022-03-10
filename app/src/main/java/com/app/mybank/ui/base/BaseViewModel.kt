package com.app.mybank.ui.base

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.app.mybank.core.usecase.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    val showError = ObservableField(View.GONE)
    val showLoader = ObservableField(View.GONE)
    val showErrorText = ObservableField<String>()

    fun <T> makeCall(
        networkCall: suspend () -> Resource<T>,
        saveCallResult: (suspend (T) -> Unit)? = null,
        errorCallback: (suspend (Resource<T>) -> Unit)? = null
    ): LiveData<Resource<T>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val responseStatus = networkCall.invoke()
            if (responseStatus.status == Resource.Status.SUCCESS) {
                saveCallResult?.invoke(responseStatus.data!!)
                emit(responseStatus)
            } else if (responseStatus.status == Resource.Status.ERROR) {
                emit(Resource.error(responseStatus.message!!, code = responseStatus.code))
                errorCallback?.invoke(responseStatus)
            }
        }

    protected fun <T> error(message: String, code: Int = 0): Resource<T> {
        return Resource.error(message, code = code)
    }

    protected fun showLoader() {
        viewModelScope.launch {
            showError.set(View.GONE)
            showLoader.set(View.VISIBLE)
        }
    }

    protected fun showResult() {
        viewModelScope.launch {
            showError.set(View.GONE)
            showLoader.set(View.GONE)
        }
    }

    protected fun showError(message: String?) {
        viewModelScope.launch {
            showError.set(View.VISIBLE)
            showLoader.set(View.GONE)
            showErrorText.set(message.orEmpty())
        }
    }

}