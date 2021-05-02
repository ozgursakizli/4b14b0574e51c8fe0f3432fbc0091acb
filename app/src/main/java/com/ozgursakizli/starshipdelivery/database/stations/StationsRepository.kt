package com.ozgursakizli.starshipdelivery.database.stations

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class StationsRepository @Inject constructor(private val stationsDao: StationsDao) : StationsDataSource {

    override fun getStations(): Flow<List<StationEntity>> {
        return stationsDao.getStations()
    }

    override suspend fun insert(stationList: List<StationEntity>) {
        return stationsDao.insert(stationList)
    }

    override suspend fun update(stationEntity: StationEntity): Int {
        return stationsDao.update(stationEntity)
    }

}