package com.ozgursakizli.starshipdelivery.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ozgursakizli.starshipdelivery.database.spaceship.SpaceshipDao
import com.ozgursakizli.starshipdelivery.database.spaceship.SpaceshipEntity

@Database(entities = [SpaceshipEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun spaceshipDao(): SpaceshipDao

}