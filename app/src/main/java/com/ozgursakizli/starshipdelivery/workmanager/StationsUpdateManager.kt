package com.ozgursakizli.starshipdelivery.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ozgursakizli.starshipdelivery.database.stations.StationsRepository
import com.ozgursakizli.starshipdelivery.network.generic.ApiResponse
import com.ozgursakizli.starshipdelivery.network.spacestations.SpaceStationsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@HiltWorker
class StationsUpdateManager @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val spaceStationsRepository: SpaceStationsRepository,
    private val stationsRepository: StationsRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        spaceStationsRepository.getSpaceStations().collect { result ->
            when (result) {
                is ApiResponse.Success -> {
                    result.data?.let { stationsRepository.insert(it) }
                }
                is ApiResponse.Error -> {
                    Timber.e("getSpaceStations:: $result")
                }
                is ApiResponse.Exception -> {
                    Timber.e("getSpaceStations::error: $result")
                }
            }
        }

        return Result.success()
    }

}