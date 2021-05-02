package com.ozgursakizli.starshipdelivery.ui.stations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
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
        initUi()
        observeViewModel()
    }

    private fun initUi() {
        Timber.d("initUi")
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
            })
            spaceStations.observe(viewLifecycleOwner, {
                items.addAll(it)
                stationsAdapter?.setData(it)
            })
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