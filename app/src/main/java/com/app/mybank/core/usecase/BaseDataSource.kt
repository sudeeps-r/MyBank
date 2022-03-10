package com.app.mybank.core.usecase

import com.app.mybank.R
import com.app.mybank.core.dao.DataStore
import com.app.mybank.core.util.CoreConstant
import com.app.mybank.core.util.NetworkConstant.TOKEN_EXPIRED
import com.app.mybank.core.util.PreferenceKey
import com.app.mybank.ui.login.service.LoginResponse
import com.app.mybank.ui.usecase.AppResource
import retrofit2.Response
import java.io.IOException

/**
 * Network handler, this is a wrapper around
 * Network call and abstract the error and emit the formatted resource
 */
abstract class BaseDataSource(private val appResource: AppResource) {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            val errorResponse = response.errorBody()?.charStream()?.readText()
            return if (errorResponse != null && response.code() != TOKEN_EXPIRED) {
                val error = errorResponse.toObject<ErrorResponse>()
                Resource.error(message = error.error, code = response.code())
            } else {
                Resource.error(
                    message = appResource.getString(R.string.un_expected_error),
                    code = response.code()
                )
            }

        } catch (e: IOException) {
            return error(appResource.getString(R.string.no_internet_connection))
        } catch (e: Exception) {
            val msg = e.message ?: e.toString()
            return error(msg)
        }
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.error(message)
    }

    protected fun saveToken(dataStore: DataStore, res: Resource<LoginResponse>) {
        when (res.status) {
            Resource.Status.SUCCESS -> takeIf { CoreConstant.API_SUCCESS == res.data?.status }?.let {
                dataStore.setValue(PreferenceKey.TOKEN, res.data?.token.orEmpty())
                dataStore.setValue(PreferenceKey.USER_NAME, res.data?.username.orEmpty())
            }
            else -> {}
        }
    }
}