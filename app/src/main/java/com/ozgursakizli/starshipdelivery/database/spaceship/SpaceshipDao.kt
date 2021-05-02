package com.ozgursakizli.starshipdelivery.database.spaceship

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ozgursakizli.starshipdelivery.database.DatabaseConstants
import kotlinx.coroutines.flow.Flow

@Dao
interface SpaceshipDao : SpaceshipDataSource {

    @Query("SELECT * FROM ${DatabaseConstants.SPACESHIP_TABLE}")
    override fun getSpaceship(): Flow<SpaceshipEntity?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    override suspend fun insert(spaceshipEntity: SpaceshipEntity): Long

    @Update
    override suspend fun update(spaceshipEntity: SpaceshipEntity): Int

}