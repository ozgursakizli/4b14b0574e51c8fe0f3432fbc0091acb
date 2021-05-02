package com.ozgursakizli.starshipdelivery.network.spacestations

import com.ozgursakizli.starshipdelivery.database.stations.StationEntity
import com.ozgursakizli.starshipdelivery.network.generic.ApiResponse
import com.ozgursakizli.starshipdelivery.network.generic.BaseRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SpaceStationsRepository @Inject constructor(
    private val spaceStationsClient: SpaceStationsClient
) : BaseRepository() {

    fun getSpaceStations(): Flow<ApiResponse<List<StationEntity>>> {
        return flow {
            val response = spaceStationsClient.getSpaceStations()
            emit(response)
        }.flowOn(dispatcher)
    }

}