package com.ozgursakizli.starshipdelivery.ui.splash

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.ozgursakizli.starshipdelivery.databinding.ActivitySplashBinding
import com.ozgursakizli.starshipdelivery.ui.main.MainActivity
import com.ozgursakizli.starshipdelivery.ui.newspaceship.NewSpaceshipActivity
import com.ozgursakizli.starshipdelivery.utilities.EventObserver
import com.ozgursakizli.starshipdelivery.utilities.StationEvents
import com.ozgursakizli.starshipdelivery.workmanager.StationsUpdateManager
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModel()
    }

    private fun observeViewModel() {
        Timber.d("observeViewModel")
        with(splashViewModel) {
            spaceShip.observe(this@SplashActivity, {
                Timber.d("spaceShip:: $it")
                if (it == null) {
                    openNextActivity(NextActivity.NEW_SPACESHIP)
                } else {
                    openNextActivity(NextActivity.MAIN_ACTIVITY)
                }
            })
            event.observe(this@SplashActivity, EventObserver(::eventHandler))
        }
    }

    private fun openNextActivity(activityEnum: NextActivity) {
        binding.lottieAnim.animate().setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                Timber.d("onAnimationStart")
            }

            override fun onAnimationEnd(animation: Animator?) {
                Timber.d("onAnimationEnd")
                when (activityEnum) {
                    NextActivity.NEW_SPACESHIP -> {
                        Intent(this@SplashActivity, NewSpaceshipActivity::class.java).apply {
                            startActivity(this)
                            finish()
                        }
                    }
                    NextActivity.MAIN_ACTIVITY -> {
                        Intent(this@SplashActivity, MainActivity::class.java).apply {
                            startActivity(this)
                            finish()
                        }
                    }
                }
            }

            override fun onAnimationCancel(animation: Animator?) {
                Timber.d("onAnimationCancel")
            }

            override fun onAnimationRepeat(animation: Animator?) {
                Timber.d("onAnimationRepeat")
            }
        }).alpha(0F).duration = 2000
    }

    private fun eventHandler(eventType: StationEvents) {
        Timber.d("eventHandler::eventType: ${eventType.javaClass.name}")

        when (eventType) {
            is StationEvents.ShouldFetch -> {
                startStationsFetchWorker()
            }
        }
    }

    private fun startStationsFetchWorker() {
        val networkConstraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val uploadWorkRequest = OneTimeWorkRequestBuilder<StationsUpdateManager>().setConstraints(networkConstraint).build()
        WorkManager.getInstance(this).enqueue(uploadWorkRequest)
    }

    private enum class NextActivity {
        NEW_SPACESHIP, MAIN_ACTIVITY
    }

}