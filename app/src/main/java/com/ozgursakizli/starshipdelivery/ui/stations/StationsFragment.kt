package com.ozgursakizli.starshipdelivery.ui.stations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Timber.d("onCreateView")
        _binding = FragmentStationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.d("onCreateView")
        super.onViewCreated(view, savedInstanceState)
        initPager()
        observeViewModel()
    }

    private fun initPager() {
        Timber.d("initPager")
        stationsAdapter = StationsAdapter(this)

        binding.apply {
            slider.adapter = stationsAdapter
            slider.offscreenPageLimit = 3
            TabLayoutMediator(sliderTabLayout, slider) { _, _ -> }.attach()
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
                stationsAdapter?.setData(it)
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

    override fun onTravelClicked(station: ApiSpaceStationModel) {
        Timber.d("onTravelClicked")
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