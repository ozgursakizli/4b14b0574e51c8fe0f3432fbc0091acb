package com.ozgursakizli.starshipdelivery.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ozgursakizli.starshipdelivery.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}