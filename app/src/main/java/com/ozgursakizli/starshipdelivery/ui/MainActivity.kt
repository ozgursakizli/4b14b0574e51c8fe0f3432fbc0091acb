package com.ozgursakizli.starshipdelivery.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ozgursakizli.starshipdelivery.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

private val TAG = MainActivity::class.java.simpleName

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModel()
    }

    private fun observeViewModel() {
        Log.d(TAG, "observeViewModel")
        with(mainViewModel) {
            spaceStations.observe(this@MainActivity, {
                Log.d(TAG, "spaceStations:: $it")
            })
        }
    }

}