package com.ozgursakizli.starshipdelivery.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ozgursakizli.starshipdelivery.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModel()
    }

    private fun observeViewModel() {
        Timber.d("observeViewModel")
        with(mainViewModel) {
            spaceShip.observe(this@MainActivity, {
                Timber.d("spaceship:: $it")

                if (it == null) {
                    binding.lottieAnim.animate().alpha(0F).duration = 2000
                }
            })
        }
    }

}