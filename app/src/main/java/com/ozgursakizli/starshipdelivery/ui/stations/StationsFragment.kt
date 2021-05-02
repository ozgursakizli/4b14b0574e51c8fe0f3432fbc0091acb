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
import com.ozgursakizli.starshipdelivery.databinding.FragmentStationsBinding
import com.ozgursakizli.starshipdelivery.models.ApiSpaceStationModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class StationsFragment : Fragment(), StationsAdapter.ItemClickListener {

    private var _binding: FragmentStationsBinding? = null
    private val binding get() = _binding!!
    private val stationsViewModel: StationsViewModel by viewModels()
    private var stationsAdapter: StationsAdapter? = null
    private var items = arrayListOf<ApiSpaceStationModel>()
    private lateinit var currentStation: ApiSpaceStationModel

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
        } else {
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
                items.addAll(it)
            })
            currentStation.observe(viewLifecycleOwner, {
                updateCurrentStation(it)
            })
        }
    }

    private fun updateUi(spaceship: SpaceshipEntity) {
        binding.apply {
            tvSpaceshipName.text = spaceship.name
            tvDamageCapacity.text = spaceship.damageCapacity.toString()
            tvDamageSeconds.text = String.format(getString(R.string.damage_seconds), spaceship.damageSeconds)
            tvUgs.text = String.format(getString(R.string.ugs), spaceship.ugs)
            tvEus.text = String.format(getString(R.string.eus), spaceship.eus)
            tvDs.text = String.format(getString(R.string.ds), spaceship.ds)
        }
    }

    private fun updateCurrentStation(apiSpaceStationModel: ApiSpaceStationModel) {
        currentStation = apiSpaceStationModel
        binding.tvCurrentStation.text = apiSpaceStationModel.name
        stationsAdapter?.setData(items, apiSpaceStationModel)
    }

    override fun onTravelClicked(station: ApiSpaceStationModel) {
        updateCurrentStation(station)
    }

    override fun onFavouriteClicked(station: ApiSpaceStationModel) {
        Timber.d("onFavouriteClicked")
    }

    override fun onDestroyView() {
        Timber.d("onDestroyView")
        _binding = null
        stationsAdapter = null
        super.onDestroyView()
    }

}