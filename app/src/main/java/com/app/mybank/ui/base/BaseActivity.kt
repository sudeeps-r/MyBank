package com.app.mybank.ui.base

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.app.mybank.R
import com.app.mybank.core.dao.DataStore
import com.app.mybank.core.usecase.Resource
import com.app.mybank.core.util.NetworkConstant
import com.app.mybank.ui.usecase.IntentNavigator
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var dataStore: DataStore
    @Inject
    lateinit var navigator: IntentNavigator

    protected fun showSnackBar(message: String) {
        Snackbar.make(
            findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    protected fun showAlertDialog(
        title: String,
        message: String,
        callback1: () -> Unit,
        callback2: (() -> Unit)? = null
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setCancelable(false)
        builder.setMessage(message)
        builder.setPositiveButton(android.R.string.yes) { dialog, _ ->
            dialog.dismiss()
            callback1.invoke()
        }
        callback2?.let {
            builder.setNegativeButton(android.R.string.no) { dialog, _ ->
                dialog.dismiss()
                callback2.invoke()
            }
        }
        builder.show()
    }

    protected fun <T> apiDefaultHandler(
        resource: Resource<T>?,
        handler: (resource: Resource<T>) -> Unit
    ) {
        if (resource == null) {
            return
        }
        when {
            resource.code == NetworkConstant.TOKEN_EXPIRED -> {
                showAlertDialog(
                    getString(R.string.app_name),
                    getString(R.string.label_session_expired),
                    {
                        dataStore.clear()
                        navigator.navigateToLogin(this)
                    })
            }
            else -> {
                handler.invoke(resource)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}