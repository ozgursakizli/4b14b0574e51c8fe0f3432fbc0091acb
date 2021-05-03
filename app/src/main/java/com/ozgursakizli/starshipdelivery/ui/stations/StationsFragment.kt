package com.ozgursakizli.starshipdelivery.ui.stations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.ozgursakizli.starshipdelivery.R
import com.ozgursakizli.starshipdelivery.database.spaceship.SpaceshipEntity
import com.ozgursakizli.starshipdelivery.database.stations.StationEntity
import com.ozgursakizli.starshipdelivery.databinding.FragmentStationsBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class StationsFragment : Fragment(), StationsAdapter.ItemClickListener {

    private var _binding: FragmentStationsBinding? = null
    private val binding get() = _binding!!
    private val stationsViewModel: StationsViewModel by viewModels()
    private var stationsAdapter: StationsAdapter? = null
    private var items = arrayListOf<StationEntity>()
    private var spaceShip: SpaceshipEntity? = null
    private var currentStation: StationEntity? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Timber.d("onCreateView")
        _binding = FragmentStationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.d("onCreateView")
        super.onViewCreated(view, savedInstanceState)
        initUi()
        observeViewModel()
    }

    private fun initUi() {
        Timber.d("initPager")
        stationsAdapter = StationsAdapter(this)

        binding.apply {
            slider.adapter = stationsAdapter
            slider.offscreenPageLimit = 3
            TabLayoutMediator(sliderTabLayout, slider) { _, _ -> }.attach()
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    onSearch(query)
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    onSearch(newText)
                    return true
                }

            })
        }
    }

    private fun onSearch(stationName: String?) {
        val station = items.find { it.name.equals(stationName, true) }

        if (station == null) {
            updateCurrentStation(currentStation)
        } else if (station != currentStation) {
            stationsAdapter?.filterStation(station)
        }
    }

    private fun observeViewModel() {
        Timber.d("observeViewModel")
        with(stationsViewModel) {
            spaceShip.observe(viewLifecycleOwner, {
                updateUi(it)
            })
            spaceStations.observe(viewLifecycleOwner, {
                items.clear()
                items.addAll(it)
                stationsAdapter?.setData(items, currentStation)

                if (currentStation == null) {
                    currentStation = items[0]
                    updateSpaceshipLocation(currentStation)
                }
            })
            fetchSpaceship()
            getSpaceStations()
        }
    }

    private fun updateUi(spaceshipEntity: SpaceshipEntity) {
        spaceShip = spaceshipEntity
        binding.apply {
            with(spaceshipEntity) {
                tvSpaceshipName.text = name
                tvDamageCapacity.text = damageCapacity.toString()
                tvDamageSeconds.text = String.format(getString(R.string.damage_seconds), damageSeconds)
                tvUgs.text = String.format(getString(R.string.ugs), ugs)
                tvEus.text = String.format(getString(R.string.eus), eus)
                tvDs.text = String.format(getString(R.string.ds), ds)
                currentStation?.let { updateCurrentStation(it) }
            }
        }
    }

    private fun updateCurrentStation(stationEntity: StationEntity?) {
        stationEntity?.let {
            currentStation = stationEntity
            binding.tvCurrentStation.text = stationEntity.name
            stationsAdapter?.setData(items, stationEntity)
        }
    }

    private fun shouldStartTravel(station: StationEntity): Boolean {
        if (spaceShip == null) return false
        if (station.id == 1L) return true

        if (spaceShip!!.ugs == 0 ||
            station.need == 0) {
            return false
        }

        return true
    }

    override fun onTravelClicked(station: StationEntity) {
        if (shouldStartTravel(station)) {
            val stationNeed: Int = station.need
            val ugsStock: Int = spaceShip!!.ugs

            if (stationNeed > ugsStock) {
                return
            }

            spaceShip!!.ugs = ugsStock - stationNeed
            station.need = 0
            station.stock = station.capacity
            stationsViewModel.updateStocks(spaceShip!!)
            stationsViewModel.updateStation(station)
            stationsViewModel.updateSpaceshipLocation(station)
        }
    }

    override fun onFavouriteClicked(station: StationEntity) {
        Timber.d("onFavouriteClicked")
        station.isFavourite = !station.isFavourite
        stationsViewModel.updateStation(station)
    }

    override fun onDestroyView() {
        Timber.d("onDestroyView")
        _binding = null
        stationsAdapter = null
        currentStation = null
        super.onDestroyView()
    }

}