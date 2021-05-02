package com.ozgursakizli.starshipdelivery.database.spaceship

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ozgursakizli.starshipdelivery.database.DatabaseConstants

@Entity(tableName = DatabaseConstants.SPACESHIP_TABLE)
data class SpaceshipEntity(
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "damage_capacity")
    val damageCapacity: Int,
    @ColumnInfo(name = "damage_seconds")
    val damageSeconds: Int,
    @ColumnInfo(name = "durability")
    val durability: Int,
    @ColumnInfo(name = "speed")
    val speed: Int,
    @ColumnInfo(name = "material_capacity")
    val materialCapacity: Int,
    @ColumnInfo(name = "ugs")
    val ugs: Int,
    @ColumnInfo(name = "eus")
    val eus: Int,
    @ColumnInfo(name = "ds")
    val ds: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    override fun toString(): String {
        return "id: $id" +
                ", name: $name" +
                ", damageCapacity: $damageCapacity" +
                ", damageSeconds: $damageSeconds" +
                ", durability: $durability" +
                ", speed: $speed" +
                ", materialCapacity: $materialCapacity" +
                ", ugs: $ugs" +
                ", eus: $eus" +
                ", ds: $ds"
    }
}
