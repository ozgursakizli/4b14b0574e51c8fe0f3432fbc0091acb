package com.ozgursakizli.starshipdelivery.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ozgursakizli.starshipdelivery.databinding.FragmentFavouritesBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    companion object {
        fun newInstance(): FavouritesFragment = FavouritesFragment()
    }

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Timber.d("onCreateView")
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.d("onCreateView")
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {

    }

    override fun onDestroyView() {
        Timber.d("onDestroyView")
        _binding = null
        super.onDestroyView()
    }

}