package com.app.mybank.ui.usecase

import android.app.Activity
import android.content.Intent
import com.app.mybank.ui.home.HomeActivity
import com.app.mybank.ui.login.LoginActivity
import com.app.mybank.ui.signup.SignUpActivity
import com.app.mybank.ui.transfer.TransferActivity

/**
 * Wrapper around application navigation
 */
interface IntentNavigator {
    fun navigateToSignup(activity: Activity)
    fun navigateToHome(activity: Activity)
    fun navigateToTransfer(activity: Activity)
    fun navigateToLogin(activity: Activity)
}


internal class IntentNavigatorImpl() : IntentNavigator {
    override fun navigateToSignup(activity: Activity) {
        val intent = Intent(activity, SignUpActivity::class.java)
        activity.startActivity(intent)
    }

    override fun navigateToHome(activity: Activity) {
        val intent = Intent(activity, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)
    }

    override fun navigateToTransfer(activity: Activity) {
        val intent = Intent(activity, TransferActivity::class.java)
        activity.startActivity(intent)
    }

    override fun navigateToLogin(activity: Activity) {
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)
    }

}