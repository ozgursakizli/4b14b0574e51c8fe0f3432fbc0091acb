package com.ozgursakizli.starshipdelivery.ui

import android.os.Bundle
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ozgursakizli.starshipdelivery.R
import com.ozgursakizli.starshipdelivery.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val totalPoints = 15
    private var maxPoint = 13
    private var minPoint = 1
    private var remainingPoints = maxPoint

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
        observeViewModel()
    }

    private fun initUi() {
        title = getString(R.string.create_new_ship)
        binding.apply {
            seekDurability.max = maxPoint
            seekSpeed.max = maxPoint
            seekCapacity.max = maxPoint
            seekDurability.progress = minPoint
            seekSpeed.progress = minPoint
            seekCapacity.progress = minPoint
            seekDurability.setOnSeekBarChangeListener(this@MainActivity)
            seekSpeed.setOnSeekBarChangeListener(this@MainActivity)
            seekCapacity.setOnSeekBarChangeListener(this@MainActivity)
            btnContinue.setOnClickListener {
                finish()
            }
            calculateRemainingPoints()
            updateTexts()
        }
    }

    private fun updateTexts() {
        binding.apply {
            tvDurability.text = String.format(getString(R.string.durability), seekDurability.progress)
            tvSpeed.text = String.format(getString(R.string.speed), seekSpeed.progress)
            tvCapacity.text = String.format(getString(R.string.material_capacity), seekCapacity.progress)
            tvTotalPoints.text = String.format(getString(R.string.spaceship_points), remainingPoints)
        }
    }

    private fun calculateRemainingPoints() {
        binding.apply {
            val givenPoints = seekDurability.progress + seekSpeed.progress + seekCapacity.progress
            remainingPoints = totalPoints - givenPoints

            if (remainingPoints < 0) {
                seekDurability.progress = seekDurability.tag as Int
                seekSpeed.progress = seekSpeed.tag as Int
                seekCapacity.progress = seekCapacity.tag as Int
            } else {
                seekDurability.tag = seekDurability.progress
                seekSpeed.tag = seekSpeed.progress
                seekCapacity.tag = seekCapacity.progress
            }
        }
    }

    private fun observeViewModel() {
        Timber.d("observeViewModel")
        with(mainViewModel) {
            spaceShip.observe(this@MainActivity, {
                Timber.d("spaceship:: $it")
            })
        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        Timber.d("onProgressChanged::progress: $progress")

        seekBar?.let {
            if (progress < minPoint) {
                it.progress = minPoint
            }

            if (remainingPoints <= 0 && (it.tag as Int) < progress) {
                it.progress = it.tag as Int
            }

            calculateRemainingPoints()
            updateTexts()
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        Timber.d("onStartTrackingTouch")
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        Timber.d("onStopTrackingTouch")
    }

}