package com.ozgursakizli.starshipdelivery.network.spacestations

import com.ozgursakizli.starshipdelivery.models.ApiSpaceStationModel
import com.ozgursakizli.starshipdelivery.network.generic.ApiResponse
import com.ozgursakizli.starshipdelivery.network.generic.callApi
import javax.inject.Inject

class SpaceStationsClient @Inject constructor(
    private val spaceStationsService: SpaceStationsService
) {
    suspend fun getSpaceStations(): ApiResponse<List<ApiSpaceStationModel>> = callApi { spaceStationsService.getSpaceStations() }
}