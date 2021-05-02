package com.ozgursakizli.starshipdelivery.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozgursakizli.starshipdelivery.database.stations.StationEntity
import com.ozgursakizli.starshipdelivery.database.stations.StationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val stationsRepository: StationsRepository
) : ViewModel() {

    private var _spaceStations = MutableLiveData<List<StationEntity>>()
    val spaceStations: LiveData<List<StationEntity>> = _spaceStations

    init {
        fetchFavouriteStations()
    }

    private fun fetchFavouriteStations() {
        Timber.d("fetchFavouriteStations")
        viewModelScope.launch {
            stationsRepository.getFavouriteStations().collect {
                _spaceStations.postValue(it)
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