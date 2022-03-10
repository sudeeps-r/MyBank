package com.app.mybank.ui.login

import android.text.Editable
import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.app.mybank.R
import com.app.mybank.core.usecase.Resource
import com.app.mybank.ui.base.BaseViewModel
import com.app.mybank.ui.login.service.LoginRepo
import com.app.mybank.ui.login.service.LoginRequest
import com.app.mybank.ui.login.service.LoginResponse
import com.app.mybank.ui.usecase.AppResource
import com.app.mybank.ui.util.ViewConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: LoginRepo,
    private val appResource: AppResource
) : BaseViewModel() {

    @VisibleForTesting
    val userName = ObservableField<String>()

    @VisibleForTesting
    val password = ObservableField<String>()
    private val login = MutableLiveData<LoginRequest>()

    val doLogin: LiveData<Resource<LoginResponse>> = login.switchMap { request ->
        makeCall(
            networkCall = {
                val response =
                    when {
                        request.userName.isEmpty() -> {
                            error(
                                message = appResource.getString(R.string.login_invalid_user_name),
                                code = ViewConstant.VALIDATION_USER_NAME
                            )
                        }

                        request.password.isEmpty() -> {
                            error(
                                message = appResource.getString(R.string.login_invalid_password),
                                code = ViewConstant.VALIDATION_PASSWORD
                            )
                        }
                        else -> {
                            showLoader()
                            repo.doLogin(request)
                        }
                    }
                response
            },
            errorCallback = {
                showResult()
            }
        )
    }

    fun setUserName(s: Editable) {
        userName.set(s.toString())
    }

    fun setPassword(s: Editable) {
        password.set(s.toString())
    }

    fun performLogin() {
        val usName = userName.get().orEmpty()
        val password = password.get().orEmpty()
        login.value = LoginRequest(userName = usName, password = password)
    }
}