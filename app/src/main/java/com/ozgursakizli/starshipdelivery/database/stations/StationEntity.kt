package com.ozgursakizli.starshipdelivery.database.stations

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.ozgursakizli.starshipdelivery.database.DatabaseConstants

@Entity(tableName = DatabaseConstants.STATIONS_TABLE)
data class StationEntity(
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
    var isFavourite: Boolean = false,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    override fun toString(): String {
        return "id: $id" +
                ", name: $name" +
                ", coordinateX: $coordinateX" +
                ", coordinateY: $coordinateY" +
                ", capacity: $capacity" +
                ", stock: $stock" +
                ", need: $need" +
                ", isFavourite: $isFavourite"
    }
}
