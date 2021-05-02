package com.ozgursakizli.starshipdelivery.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.ozgursakizli.starshipdelivery.R
import com.ozgursakizli.starshipdelivery.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
        observeViewModel()
    }

    private fun initUi() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navigationHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navHostFragment.navController)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.stationsFragment, R.id.favouritesFragment))
        setupActionBarWithNavController(navHostFragment.navController, appBarConfiguration)
    }

    private fun observeViewModel() {
        Timber.d("observeViewModel")
        with(mainViewModel) {
            spaceShip.observe(this@MainActivity, {
            })
            spaceStations.observe(this@MainActivity, {
            })
        }
    }

}