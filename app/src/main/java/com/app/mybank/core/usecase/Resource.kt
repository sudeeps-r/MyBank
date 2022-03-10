package com.app.mybank.core.usecase

/**
 * Repo/ViewModel always emit three type of resources
 * and which UI can handle it later
 */
data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val code: Int = 0
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null, code: Int = 0): Resource<T> {
            return Resource(Status.ERROR, data, message, code)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}