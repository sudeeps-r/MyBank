package com.app.mybank.ui.usecase

import android.content.Context
import androidx.annotation.StringRes

/**
 * AppResource is wrapper to extract the string resource
 *
 */
interface AppResource {
    fun getString(@StringRes id: Int): String
    fun getString(@StringRes id: Int, arg: String): String
}

internal class AppResourceImpl(private val context: Context) : AppResource {
    override fun getString(id: Int): String {
        return context.getString(id)
    }

    override fun getString(id: Int, arg: String): String {
        return String.format(getString(id), arg)
    }

}