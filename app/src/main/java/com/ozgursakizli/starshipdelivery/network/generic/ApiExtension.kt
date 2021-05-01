package com.ozgursakizli.starshipdelivery.network.generic

import retrofit2.Response

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun <T : Any> callApi(call: suspend () -> Response<T>): ApiResponse<T> = try {
    val response = call.invoke()
    if (response.isSuccessful) {
        ApiResponse.Success(response)
    } else {
        ApiResponse.Error(response)
    }
} catch (e: Exception) {
    ApiResponse.Exception(e)
}
