package com.ozgursakizli.starshipdelivery.network.spacestations

import com.ozgursakizli.starshipdelivery.models.ApiSpaceStationModel
import com.ozgursakizli.starshipdelivery.network.generic.ApiResult
import com.ozgursakizli.starshipdelivery.network.generic.BaseRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SpaceStationsRepository @Inject constructor(private val spaceStationsApi: SpaceStationsApi) : BaseRepository() {

    fun getSpaceStations(): Flow<ApiResult<List<ApiSpaceStationModel>>> {
        return flow {
            val response = spaceStationsApi.getSpaceStations()
            emit(response)
        }.flowOn(dispatcher)
    }

}