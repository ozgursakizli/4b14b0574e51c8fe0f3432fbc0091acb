package com.ozgursakizli.starshipdelivery.network.generic

import retrofit2.Response

sealed class ApiResponse<out T> {
    data class Success<T>(val response: Response<T>) : ApiResponse<T>() {
        val data: T? = response.body()
    }

    data class Error<T>(val response: Response<T>) : ApiResponse<T>() {
        private val statusCode: ErrorCodes = getStatusCodeFromResponse(response)
        override fun toString(): String = "Error::statusCode: $statusCode, errorBody: ${response.message()}"
    }

    data class Exception<T>(val exception: kotlin.Exception) : ApiResponse<T>() {
        override fun toString(): String = "Exception::message=$exception)"
    }

    fun <T> getStatusCodeFromResponse(response: Response<T>): ErrorCodes {
        return ErrorCodes.values().find { it.code == response.code() } ?: ErrorCodes.UNKNOWN
    }
}