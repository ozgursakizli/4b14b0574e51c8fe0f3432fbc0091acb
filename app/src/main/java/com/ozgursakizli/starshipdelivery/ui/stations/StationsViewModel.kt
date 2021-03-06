package com.ozgursakizli.starshipdelivery.ui.stations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozgursakizli.starshipdelivery.database.spaceship.SpaceshipEntity
import com.ozgursakizli.starshipdelivery.database.spaceship.SpaceshipRepository
import com.ozgursakizli.starshipdelivery.database.stations.StationEntity
import com.ozgursakizli.starshipdelivery.database.stations.StationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class StationsViewModel @Inject constructor(
    private val spaceshipRepository: SpaceshipRepository,
    private val stationsRepository: StationsRepository
) : ViewModel() {

    private var _spaceShip = MutableLiveData<SpaceshipEntity>()
    val spaceShip: LiveData<SpaceshipEntity> = _spaceShip
    private var _spaceStations = MutableLiveData<List<StationEntity>>()
    val spaceStations: LiveData<List<StationEntity>> = _spaceStations

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
            stationsRepository.getStations().collect {
                _spaceStations.postValue(it)
            }
        }
    }

    fun updateStocks(spaceshipEntity: SpaceshipEntity) {
        Timber.d("updateStocks::spaceshipEntity: $spaceshipEntity")
        viewModelScope.launch {
            spaceshipRepository.update(spaceshipEntity)
            _spaceShip.postValue(spaceshipEntity)
        }
    }

    fun updateSpaceshipLocation(station: StationEntity?) {
        Timber.d("updateSpaceship::station: $station")
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

    fun updateStation(station: StationEntity) {
        Timber.d("updateStation::station: $station")
        viewModelScope.launch {
            stationsRepository.update(station)
        }
    }

}