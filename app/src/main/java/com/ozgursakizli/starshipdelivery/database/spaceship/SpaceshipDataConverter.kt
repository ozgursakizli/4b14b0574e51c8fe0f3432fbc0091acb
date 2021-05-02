package com.ozgursakizli.starshipdelivery.database.spaceship

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.ozgursakizli.starshipdelivery.database.stations.StationEntity

class SpaceshipDataConverter {

    @TypeConverter
    fun modelToJson(value: StationEntity?): String? {
        if (value == null) return null
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToModel(value: String?): StationEntity? {
        if (value == null) return null
        return Gson().fromJson(value, StationEntity::class.java)
    }

}