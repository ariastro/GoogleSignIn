package com.astronout.googlesignin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Int = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        setupDelayTime()

        Glide.with(this)
            .load(R.drawable.logotugu)
            .into(logoSplash)

    }

    private fun setupDelayTime() {
        Handler().postDelayed({ nextActivity() }, SPLASH_TIME_OUT.toLong())
    }

    private fun nextActivity() {
        startActivity(newIntent(this))
        finish()
    }

    fun newIntent(context: Context): Intent {
        return Intent(context, MainActivity::class.java)
    }

}
