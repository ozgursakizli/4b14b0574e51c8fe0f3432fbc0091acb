package com.ozgursakizli.starshipdelivery.ui.stations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozgursakizli.starshipdelivery.database.spaceship.SpaceshipEntity
import com.ozgursakizli.starshipdelivery.database.spaceship.SpaceshipRepository
import com.ozgursakizli.starshipdelivery.models.ApiSpaceStationModel
import com.ozgursakizli.starshipdelivery.network.generic.ApiResponse
import com.ozgursakizli.starshipdelivery.network.spacestations.SpaceStationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class StationsViewModel @Inject constructor(
    private val spaceStationsRepository: SpaceStationsRepository,
    private val spaceshipRepository: SpaceshipRepository
) : ViewModel() {

    private var _spaceShip = MutableLiveData<SpaceshipEntity>()
    val spaceShip: LiveData<SpaceshipEntity> = _spaceShip
    private var _spaceStations = MutableLiveData<List<ApiSpaceStationModel>>()
    val spaceStations: LiveData<List<ApiSpaceStationModel>> = _spaceStations

    fun fetchSpaceship() {
        Timber.d("fetchSpaceship")
        viewModelScope.launch {
            spaceshipRepository.getSpaceship().collect {
                _spaceShip.postValue(it)
            }
        }
    }

    fun getSpaceStations() {
        Timber.d("getSpaceStations")
        viewModelScope.launch {
            spaceStationsRepository.getSpaceStations().collect { result ->
                when (result) {
                    is ApiResponse.Success -> {
                        _spaceStations.postValue(result.data)
                    }
                    is ApiResponse.Error -> {
                        Timber.e("getSpaceStations:: $result")
                    }
                    is ApiResponse.Exception -> Timber.e("getSpaceStations::error: $result")
                }
            }
        }
    }

    fun updateSpaceshipLocation(station: ApiSpaceStationModel?) {
        Timber.d("updateSpaceshipLocation::station: $station")
        station?.let {
            viewModelScope.launch {
                _spaceShip.value?.let { ship ->
                    ship.currentStation = station
                    val result = spaceshipRepository.update(ship)
                    Timber.d("updateSpaceshipLocation::result: $result, ship: $ship")
                }
            }
        }
    }

}