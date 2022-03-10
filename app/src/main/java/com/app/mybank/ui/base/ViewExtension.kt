package com.app.mybank.ui.base

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputLayout

/**
 * Binding Utility
 */
inline fun <reified T : ViewDataBinding> ViewGroup.inflate(
    @LayoutRes id: Int,
): T {
    return DataBindingUtil.inflate(LayoutInflater.from(context), id, this, false)
}

inline fun <reified T : ViewDataBinding> BaseActivity.inflate(@LayoutRes id: Int): T {
    return DataBindingUtil.setContentView(this, id)
}

fun Activity.hideKeyBoard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (inputMethodManager.isAcceptingText)
        inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, /*flags:*/ 0)
}

fun BaseActivity.showToolbar(
    title: Int? = null,
    mToolbar: MaterialToolbar,
    backEnabled: Boolean = false
) {
    setSupportActionBar(mToolbar)
    supportActionBar?.apply {
        setTitle("")
        title?.let {
            setTitle(title)
        }
        setDisplayShowHomeEnabled(backEnabled)
        setDisplayHomeAsUpEnabled(backEnabled)
    }
}

fun TextInputLayout.clearError() {
    error = null
    isErrorEnabled = false
}