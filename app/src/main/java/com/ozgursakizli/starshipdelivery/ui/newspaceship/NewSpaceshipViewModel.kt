package com.ozgursakizli.starshipdelivery.ui.newspaceship

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozgursakizli.starshipdelivery.database.spaceship.SpaceshipEntity
import com.ozgursakizli.starshipdelivery.database.spaceship.SpaceshipRepository
import com.ozgursakizli.starshipdelivery.utilities.Event
import com.ozgursakizli.starshipdelivery.utilities.SpaceshipEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class NewSpaceshipViewModel @Inject constructor(
    private val spaceshipRepository: SpaceshipRepository
) : ViewModel() {

    private var _spaceShip = MutableLiveData<SpaceshipEntity>()
    val spaceShip: LiveData<SpaceshipEntity> = _spaceShip
    private val _event = MutableLiveData<Event<SpaceshipEvents>>()
    val event: LiveData<Event<SpaceshipEvents>> get() = _event

    init {
        fetchSpaceship()
    }

    private fun fetchSpaceship() {
        Timber.d("fetchSpaceship")
        viewModelScope.launch {
            spaceshipRepository.getSpaceship().collect { _spaceShip.postValue(it) }
        }
    }

    fun insertShip(spaceshipEntity: SpaceshipEntity) {
        Timber.d("insertShip::spaceshipEntity: $spaceshipEntity")
        viewModelScope.launch {
            val id = spaceshipRepository.insert(spaceshipEntity)

            if (id > 0) {
                _event.postValue(Event(SpaceshipEvents.SaveSuccess))
            } else {
                _event.postValue(Event(SpaceshipEvents.SaveFailed))
            }
        }
    }

}