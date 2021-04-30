package com.ozgursakizli.starshipdelivery.network.spacestations

import com.ozgursakizli.starshipdelivery.models.ApiSpaceStationModel
import com.ozgursakizli.starshipdelivery.network.generic.ApiResult
import com.ozgursakizli.starshipdelivery.network.generic.callApi
import javax.inject.Inject

class SpaceStationsApi @Inject constructor(private val spaceStationsService: SpaceStationsService) : ApiSpaceStations {
    override suspend fun getSpaceStations(): ApiResult<List<ApiSpaceStationModel>> = callApi { spaceStationsService.getSpaceStations() }
}