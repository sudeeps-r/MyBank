package com.app.mybank.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.mybank.core.usecase.Resource
import com.app.mybank.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {

    val loginNavigation = MutableLiveData<Resource.Status>()

    fun initSplashScreen() {
        viewModelScope.launch {
            delay(2000)
            loginNavigation.value = Resource.Status.ERROR
        }
    }
}