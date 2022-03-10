package com.app.mybank.ui.signup

import android.os.Bundle
import androidx.activity.viewModels
import com.app.mybank.R
import com.app.mybank.core.usecase.Resource
import com.app.mybank.databinding.SignupActivityBinding
import com.app.mybank.ui.base.*
import com.app.mybank.ui.util.ViewConstant

class SignUpActivity : BaseActivity() {

    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var binder: SignupActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = inflate(R.layout.signup_activity)
        binder.vm = viewModel
        showToolbar(title = R.string.label_register, mToolbar = binder.toolbar, backEnabled = true)
        viewModel.doSignUp.observe(this, {
            hideKeyBoard()
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    showAlertDialog(
                        getString(R.string.app_name),
                        getString(R.string.register_success),
                        {
                            finish()
                        })
                }
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
                else -> {

                }
            }
        })
    }

}