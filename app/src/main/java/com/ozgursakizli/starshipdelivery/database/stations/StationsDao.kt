package com.ozgursakizli.starshipdelivery.database.stations

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ozgursakizli.starshipdelivery.database.DatabaseConstants
import kotlinx.coroutines.flow.Flow

@Dao
interface StationsDao : StationsDataSource {

    @Query("SELECT * FROM ${DatabaseConstants.STATIONS_TABLE}")
    override fun getStations(): Flow<List<StationEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    override suspend fun insert(stationList: List<StationEntity>)

    @Update
    override suspend fun update(stationEntity: StationEntity): Int

}