package com.ozgursakizli.starshipdelivery.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ozgursakizli.starshipdelivery.database.spaceship.SpaceshipDao
import com.ozgursakizli.starshipdelivery.database.spaceship.SpaceshipDataConverter
import com.ozgursakizli.starshipdelivery.database.spaceship.SpaceshipEntity
import com.ozgursakizli.starshipdelivery.database.stations.StationEntity
import com.ozgursakizli.starshipdelivery.database.stations.StationsDao
import com.ozgursakizli.starshipdelivery.database.stations.StationsDataConverter

@Database(entities = [SpaceshipEntity::class, StationEntity::class], version = 1, exportSchema = false)
@TypeConverters(SpaceshipDataConverter::class, StationsDataConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun spaceshipDao(): SpaceshipDao
    abstract fun stationsDao(): StationsDao

}