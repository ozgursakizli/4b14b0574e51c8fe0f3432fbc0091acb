package com.ozgursakizli.starshipdelivery.models

import com.google.gson.annotations.SerializedName

data class ApiSpaceStationModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("coordinateX")
    val coordinateX: Float,
    @SerializedName("coordinateY")
    val coordinateY: Float,
    @SerializedName("capacity")
    val capacity: Int,
    @SerializedName("stock")
    val stock: Int,
    @SerializedName("need")
    val need: Int,
)

/* Example response
{
  "name": "TCRKL289",
  "coordinateX": -2.0,s
  "coordinateY": 4.0,
  "capacity": 5000,
  "stock":2000,
  "need":3000
}
*/