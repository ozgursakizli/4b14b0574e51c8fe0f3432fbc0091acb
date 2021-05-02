package com.ozgursakizli.starshipdelivery.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ozgursakizli.starshipdelivery.database.stations.StationEntity
import com.ozgursakizli.starshipdelivery.databinding.FragmentFavouritesBinding
import com.ozgursakizli.starshipdelivery.utilities.setVisibility
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FavouritesFragment : Fragment(), FavouriteStationsAdapter.ItemClickListener {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    private val favouritesViewModel: FavouritesViewModel by viewModels()
    private var favouriteStationsAdapter: FavouriteStationsAdapter? = null
    private var items = arrayListOf<StationEntity>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Timber.d("onCreateView")
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
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
        favouriteStationsAdapter = FavouriteStationsAdapter(this)

        binding.apply {
            rvFavouriteStations.apply {
                adapter = favouriteStationsAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
        }
    }

    private fun observeViewModel() {
        Timber.d("observeViewModel")
        with(favouritesViewModel) {
            spaceStations.observe(viewLifecycleOwner, {
                items.clear()
                items.addAll(it)
                favouriteStationsAdapter?.setData(items)
                binding.apply {
                    emptyList.setVisibility(it.isEmpty())
                    rvFavouriteStations.setVisibility(it.isNotEmpty())
                }
            })
        }
    }

    override fun onItemClicked(stationEntity: StationEntity) {
        stationEntity.isFavourite = false
        favouritesViewModel.updateStation(stationEntity)
    }

    override fun onDestroyView() {
        Timber.d("onDestroyView")
        _binding = null
        favouriteStationsAdapter = null
        super.onDestroyView()
    }

}