package com.app.mybank.ui.login

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.app.mybank.R
import com.app.mybank.core.usecase.Resource
import com.app.mybank.databinding.LoginActivityBinding
import com.app.mybank.ui.base.*
import com.app.mybank.ui.util.ViewConstant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binder: LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = inflate(R.layout.login_activity)
        binder.vm = viewModel
        showToolbar(title = R.string.label_login, mToolbar = binder.toolbar, backEnabled = false)
        viewModel.doLogin.observe(this, {
            hideKeyBoard()
            when (it.status) {
                Resource.Status.SUCCESS -> navigator.navigateToHome(this)
                Resource.Status.ERROR -> {
                    binder.tinUsername.clearError()
                    binder.tinPassword.clearError()
                    when (it.code) {
                        ViewConstant.VALIDATION_USER_NAME -> {
                            binder.tinUsername.error = it.message
                        }
                        ViewConstant.VALIDATION_PASSWORD -> {
                            binder.tinPassword.error = it.message
                        }
                        else -> {
                            showSnackBar(it.message.orEmpty())
                        }
                    }


                }
                else -> {}
            }
        })
    }

    fun openSignup(view: View) {
        navigator.navigateToSignup(this)
    }
}