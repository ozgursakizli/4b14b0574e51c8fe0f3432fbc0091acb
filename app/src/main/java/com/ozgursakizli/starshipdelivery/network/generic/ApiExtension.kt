package com.ozgursakizli.starshipdelivery.network.generic

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ozgursakizli.starshipdelivery.R
import com.ozgursakizli.starshipdelivery.application.StarshipDeliveryApplication
import retrofit2.Response

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun <T : Any> callApi(call: suspend () -> Response<T>): ApiResult<T> = try {
    val response = call.invoke()
    if (response.isSuccessful) {
        ApiResult.Success(response.body()!!)
    } else {
        val gson: Gson = GsonBuilder().create()
        val apiError = gson.fromJson(response.errorBody()!!.string(), ApiError::class.java)
        Log.e("BaseApi", "callApi::call: ${call.javaClass}, error: $apiError")
        ApiResult.Error(apiError)
    }
} catch (e: Exception) {
    Log.e("BaseApi", "callApi::call: ${call.javaClass}", e)
    ApiResult.Error(ApiError(ApiErrorBody(StarshipDeliveryApplication.getInstance().getString(R.string.generic_error), ErrorCodes.UNKNOWN)))
}
