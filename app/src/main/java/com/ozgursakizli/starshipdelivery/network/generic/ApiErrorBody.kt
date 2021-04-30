package com.ozgursakizli.starshipdelivery.network.generic

import com.google.gson.annotations.SerializedName

data class ApiErrorBody(
    @SerializedName("message")
    val message: String,
    @SerializedName("status_code")
    val statusCode: Int
)