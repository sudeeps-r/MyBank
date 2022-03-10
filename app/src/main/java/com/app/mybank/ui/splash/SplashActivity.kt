package com.app.mybank.ui.splash

import android.os.Bundle
import androidx.activity.viewModels
import com.app.mybank.R
import com.app.mybank.ui.base.BaseActivity


class SplashActivity : BaseActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        viewModel.initSplashScreen()
        viewModel.loginNavigation.observe(this, {
            navigator.navigateToLogin(this)
        })
    }
}