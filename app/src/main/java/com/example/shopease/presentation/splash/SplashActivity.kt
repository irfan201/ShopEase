package com.example.shopease.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.shopease.databinding.ActivitySplashBinding
import com.example.shopease.presentation.login.LoginActivity
import com.example.shopease.presentation.onBoarding.OnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Handler(mainLooper).postDelayed({
//            val isStart = viewModel.getStart()
//            if (isStart) {
//                startActivity(Intent(this, LoginActivity::class.java))
//                finish()
//            } else {
//                startActivity(Intent(this, OnBoardingActivity::class.java))
//                finish()
//            }
//        }, 3000)

        lifecycleScope.launch {
            delay(3000)
            val isStart = viewModel.getStart()
            if (isStart) {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashActivity, OnBoardingActivity::class.java))
                finish()
            }
        }
    }
}