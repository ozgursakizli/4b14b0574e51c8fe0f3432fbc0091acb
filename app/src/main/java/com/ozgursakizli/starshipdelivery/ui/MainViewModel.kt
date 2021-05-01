package com.ozgursakizli.starshipdelivery.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozgursakizli.starshipdelivery.models.ApiSpaceStationModel
import com.ozgursakizli.starshipdelivery.network.generic.ApiResponse
import com.ozgursakizli.starshipdelivery.network.spacestations.SpaceStationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private val TAG = MainViewModel::class.java.simpleName

@HiltViewModel
class MainViewModel @Inject constructor(
    private val spaceStationsRepository: SpaceStationsRepository
) : ViewModel() {

    private var _spaceStations = MutableLiveData<List<ApiSpaceStationModel>>()
    val spaceStations: LiveData<List<ApiSpaceStationModel>> = _spaceStations

    init {
        getSpaceStations()
    }

    private fun getSpaceStations() {
        Log.d(TAG, "getSpaceStations")
        viewModelScope.launch {
            spaceStationsRepository.getSpaceStations().collect { result ->
                when (result) {
                    is ApiResponse.Success -> {
                        _spaceStations.postValue(result.data)
                    }
                    is ApiResponse.Error -> {
                        Log.e(TAG, "getSpaceStations:: $result")
                    }
                    is ApiResponse.Exception -> Log.e(TAG, "getSpaceStations::error: $result")
                }
            }
        }
    }

}