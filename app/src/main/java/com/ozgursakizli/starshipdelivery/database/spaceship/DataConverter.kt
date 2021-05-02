package com.ozgursakizli.starshipdelivery.database.spaceship

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.ozgursakizli.starshipdelivery.models.ApiSpaceStationModel

class DataConverter {

    @TypeConverter
    fun modelToJson(value: ApiSpaceStationModel?): String? {
        if (value == null) return null
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToModel(value: String?): ApiSpaceStationModel? {
        if (value == null) return null
        return Gson().fromJson(value, ApiSpaceStationModel::class.java)
    }

}