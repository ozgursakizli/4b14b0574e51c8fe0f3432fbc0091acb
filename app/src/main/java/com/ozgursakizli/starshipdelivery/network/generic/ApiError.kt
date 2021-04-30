package com.ozgursakizli.starshipdelivery.network.generic

import com.google.gson.annotations.SerializedName

open class ApiError(
    @SerializedName("status") val status: ApiErrorBody,
)