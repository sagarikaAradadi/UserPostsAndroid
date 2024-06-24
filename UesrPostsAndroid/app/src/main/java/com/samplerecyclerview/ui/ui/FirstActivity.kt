package com.samplerecyclerview.ui.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import com.samplerecyclerview.R
import com.samplerecyclerview.databinding.ActivityFirstBinding
import com.samplerecyclerview.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstActivity : AppCompatActivity() {
    lateinit var binding: ActivityFirstBinding
    var keepSplashOnScreen = true
    private val delay = 1000L


    override fun onCreate(savedInstanceState: Bundle?) {
        //install splash
        installSplashScreen().setKeepOnScreenCondition { keepSplashOnScreen }
        Handler(Looper.getMainLooper()).postDelayed({ keepSplashOnScreen = false }, delay)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_first)
        initUI()
    }

    private fun initUI() {

        binding.openBt.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}