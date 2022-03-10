package com.app.mybank.ui.signup

import android.text.Editable
import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.app.mybank.R
import com.app.mybank.core.usecase.Resource
import com.app.mybank.ui.base.BaseViewModel
import com.app.mybank.ui.login.service.LoginResponse
import com.app.mybank.ui.signup.service.RegisterRequest
import com.app.mybank.ui.signup.service.SignUpRepo
import com.app.mybank.ui.usecase.AppResource
import com.app.mybank.ui.util.ViewConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repo: SignUpRepo,
    private val appResource: AppResource
) : BaseViewModel() {

    @VisibleForTesting
    val userName = ObservableField<String>()

    @VisibleForTesting
    val password = ObservableField<String>()

    @VisibleForTesting
    val confirmPassword = ObservableField<String>()
    private val signUp = MutableLiveData<RegisterRequest>()

    val doSignUp: LiveData<Resource<LoginResponse>> = signUp.switchMap { request ->
        makeCall(
            networkCall = {
                val response =
                    when {
                        request.userName.isNullOrEmpty() -> {
                            error(
                                appResource.getString(R.string.login_invalid_user_name),
                                ViewConstant.VALIDATION_USER_NAME
                            )
                        }

                        request.password.isNullOrEmpty() -> {
                            error(
                                appResource.getString(R.string.login_invalid_password),
                                ViewConstant.VALIDATION_PASSWORD
                            )
                        }

                        request.password != confirmPassword.get().orEmpty() -> {
                            error(
                                appResource.getString(R.string.signup_password_valid_error),
                                ViewConstant.VALIDATION_PASSWORD
                            )
                        }

                        else -> {
                            showLoader()
                            repo.doRegister(request)
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

    fun setConfirmPassword(s: Editable) {
        confirmPassword.set(s.toString())
    }

    fun doRegister() {
        val request = RegisterRequest(
            userName = userName.get().orEmpty(),
            password = password.get().orEmpty()
        )
        signUp.value = request
    }
}