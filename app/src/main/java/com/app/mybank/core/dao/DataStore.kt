package com.app.mybank.core.dao

import android.content.Context
import android.content.SharedPreferences

/**
 * Data store is wrapper around shared preference
 */
interface DataStore {

    fun setValue(key: String, value: String)
    fun getString(key: String, default: String? = null): String?
    fun clear()

}

internal class DataStoreImpl(
    context: Context,
    file: String
) : DataStore {
    private val mPref = context.getSharedPreferences(
        file,
        Context.MODE_PRIVATE
    )

    override fun setValue(key: String, value: String) {
        edit {
            it.putString(key, value)
        }
    }

    override fun getString(key: String, default: String?): String? {
        return getValue(
            key = key, default = default
        )
    }

    override fun clear() {
        edit { it.clear() }
    }

    private inline fun edit(value: (SharedPreferences.Editor) -> Unit) {
        val edit = mPref.edit()
        value(edit)
        edit.apply()
    }

    private inline fun <reified T> getValue(key: String, default: T?): T? {
        return mPref.all[key] as? T ?: default
    }
}