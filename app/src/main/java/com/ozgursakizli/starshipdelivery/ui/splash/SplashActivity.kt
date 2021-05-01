package com.ozgursakizli.starshipdelivery.ui.splash

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ozgursakizli.starshipdelivery.databinding.ActivitySplashBinding
import com.ozgursakizli.starshipdelivery.ui.newspaceship.NewSpaceshipActivity
import timber.log.Timber

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lottieAnim.animate().setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                Timber.d("onAnimationStart")
            }

            override fun onAnimationEnd(animation: Animator?) {
                Timber.d("onAnimationEnd")
                Intent(this@SplashActivity, NewSpaceshipActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }

            override fun onAnimationCancel(animation: Animator?) {
                Timber.d("onAnimationCancel")
            }

            override fun onAnimationRepeat(animation: Animator?) {
                Timber.d("onAnimationRepeat")
            }

        }).alpha(0F).duration = 3000
    }

}