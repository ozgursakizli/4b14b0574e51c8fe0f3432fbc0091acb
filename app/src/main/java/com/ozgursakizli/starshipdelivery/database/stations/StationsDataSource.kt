package com.ozgursakizli.starshipdelivery.database.stations

import kotlinx.coroutines.flow.Flow

interface StationsDataSource {
    fun getStations(): Flow<List<StationEntity>>
    suspend fun insert(stationList: List<StationEntity>)
    suspend fun update(stationEntity: StationEntity): Int
}