package com.ozgursakizli.starshipdelivery.network.spacestations

import com.ozgursakizli.starshipdelivery.database.stations.StationEntity
import retrofit2.Response
import retrofit2.http.GET

interface SpaceStationsService {
    @GET("e7211664-cbb6-4357-9c9d-f12bf8bab2e2")
    suspend fun getSpaceStations(): Response<List<StationEntity>>
}