package com.ozgursakizli.starshipdelivery.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozgursakizli.starshipdelivery.database.spaceship.SpaceshipEntity
import com.ozgursakizli.starshipdelivery.database.spaceship.SpaceshipRepository
import com.ozgursakizli.starshipdelivery.database.stations.StationsRepository
import com.ozgursakizli.starshipdelivery.utilities.Event
import com.ozgursakizli.starshipdelivery.utilities.StationEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val spaceshipRepository: SpaceshipRepository,
    private val stationsRepository: StationsRepository
) : ViewModel() {

    private var _spaceShip = MutableLiveData<SpaceshipEntity>()
    val spaceShip: LiveData<SpaceshipEntity> = _spaceShip
    private val _event = MutableLiveData<Event<StationEvents>>()
    val event: LiveData<Event<StationEvents>> get() = _event

    init {
        fetchSpaceship()
        fetchStations()
    }

    private fun fetchSpaceship() {
        Timber.d("fetchSpaceship")
        viewModelScope.launch {
            spaceshipRepository.getSpaceship().collect { _spaceShip.postValue(it) }
        }
    }

    private fun fetchStations() {
        Timber.d("fetchStations")
        viewModelScope.launch {
            stationsRepository.getStations().collect {
                if (it.isNullOrEmpty()) {
                    _event.postValue(Event(StationEvents.ShouldFetch))
                }
            }
        }
    }

}