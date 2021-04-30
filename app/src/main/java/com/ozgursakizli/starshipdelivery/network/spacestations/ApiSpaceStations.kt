package com.ozgursakizli.starshipdelivery.network.spacestations

import com.ozgursakizli.starshipdelivery.models.ApiSpaceStationModel
import com.ozgursakizli.starshipdelivery.network.generic.ApiResult

interface ApiSpaceStations {
    suspend fun getSpaceStations(): ApiResult<List<ApiSpaceStationModel>>
}