package com.ozgursakizli.starshipdelivery.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ozgursakizli.starshipdelivery.databinding.FragmentStationsBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class StationsFragment : Fragment() {

    companion object {
        fun newInstance(): StationsFragment = StationsFragment()
    }

    private var _binding: FragmentStationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Timber.d("onCreateView")
        _binding = FragmentStationsBinding.inflate(inflater, container, false)
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