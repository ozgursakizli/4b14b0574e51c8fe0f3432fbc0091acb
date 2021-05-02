package com.ozgursakizli.starshipdelivery.ui.newspaceship

import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ozgursakizli.starshipdelivery.R
import com.ozgursakizli.starshipdelivery.database.spaceship.SpaceshipEntity
import com.ozgursakizli.starshipdelivery.databinding.ActivityNewSpaceshipBinding
import com.ozgursakizli.starshipdelivery.ui.main.MainActivity
import com.ozgursakizli.starshipdelivery.utilities.EventObserver
import com.ozgursakizli.starshipdelivery.utilities.SpaceshipEvents
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class NewSpaceshipActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    private lateinit var binding: ActivityNewSpaceshipBinding
    private val newSpaceshipViewModel: NewSpaceshipViewModel by viewModels()
    private val totalPoints = 15
    private var maxPoint = 13
    private var minPoint = 1
    private var remainingPoints = maxPoint

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivityNewSpaceshipBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
        observeViewModel()
    }

    private fun initUi() {
        Timber.d("initUi")
        title = getString(R.string.create_new_ship)
        binding.apply {
            seekDurability.max = maxPoint
            seekSpeed.max = maxPoint
            seekCapacity.max = maxPoint
            seekDurability.progress = minPoint
            seekSpeed.progress = minPoint
            seekCapacity.progress = minPoint
            seekDurability.setOnSeekBarChangeListener(this@NewSpaceshipActivity)
            seekSpeed.setOnSeekBarChangeListener(this@NewSpaceshipActivity)
            seekCapacity.setOnSeekBarChangeListener(this@NewSpaceshipActivity)
            btnContinue.setOnClickListener { createShipAndFinish() }
            calculateRemainingPoints()
            updateTexts()
        }
    }

    private fun updateTexts() {
        Timber.d("updateTexts")
        binding.apply {
            tvDurability.text = String.format(getString(R.string.durability), seekDurability.progress)
            tvSpeed.text = String.format(getString(R.string.speed), seekSpeed.progress)
            tvCapacity.text = String.format(getString(R.string.material_capacity), seekCapacity.progress)
            tvTotalPoints.text = String.format(getString(R.string.spaceship_points), remainingPoints)
        }
    }

    private fun calculateRemainingPoints() {
        Timber.d("calculateRemainingPoints")
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
        with(newSpaceshipViewModel) {
            spaceShip.observe(this@NewSpaceshipActivity, {
                Timber.d("spaceship:: $it")
            })
            event.observe(this@NewSpaceshipActivity, EventObserver(::eventHandler))
        }
    }

    private fun createShipAndFinish() {
        Timber.d("createShipAndFinish")
        binding.apply {
            if (edtShipName.text.toString().trim().isEmpty()) {
                edtShipName.error = getString(R.string.empty_name_error)
                return
            }

            if (remainingPoints != 0) {
                tvTotalPoints.setTextColor(ContextCompat.getColor(this@NewSpaceshipActivity, R.color.red))
                Toast.makeText(this@NewSpaceshipActivity, R.string.remaining_points_error, Toast.LENGTH_SHORT).show()
                return
            }
        }

        val spaceShip = SpaceshipEntity(
            name = binding.edtShipName.text.toString().trim(),
            damageCapacity = 100,
            durability = binding.seekDurability.progress,
            speed = binding.seekSpeed.progress,
            materialCapacity = binding.seekCapacity.progress)
        newSpaceshipViewModel.insertShip(spaceShip)
    }

    private fun eventHandler(eventType: SpaceshipEvents) {
        Timber.d("eventHandler::eventType: ${eventType.javaClass.name}")

        when (eventType) {
            is SpaceshipEvents.SaveSuccess -> {
                Intent(this@NewSpaceshipActivity, MainActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }
            is SpaceshipEvents.SaveFailed -> {
                Toast.makeText(this@NewSpaceshipActivity, R.string.error_occurred, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        Timber.d("onProgressChanged::progress: $progress")
        binding.tvTotalPoints.setTextColor(ContextCompat.getColor(this@NewSpaceshipActivity, R.color.black))

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