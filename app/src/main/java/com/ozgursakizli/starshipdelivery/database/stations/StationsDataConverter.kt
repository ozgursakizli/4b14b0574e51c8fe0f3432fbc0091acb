package com.ozgursakizli.starshipdelivery.database.stations

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StationsDataConverter {

    @TypeConverter
    fun listToJson(value: List<StationEntity>?): String? {
        if (value == null) return null
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String?): List<StationEntity>? {
        if (value == null) return null
        return Gson().fromJson<List<StationEntity>>(value, object : TypeToken<List<StationEntity>>() {}.type)
    }

}