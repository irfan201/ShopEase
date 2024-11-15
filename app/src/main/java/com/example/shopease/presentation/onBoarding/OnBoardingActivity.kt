package com.example.shopease.presentation.onBoarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.shopease.R
import com.example.shopease.databinding.ActivityOnboardingBinding
import com.example.shopease.presentation.MainActivity
import com.example.shopease.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private val viewModel: OnBoardingViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnStart.setOnClickListener {
            lifecycleScope.launch {
                viewModel.saveStart(true)
                if (viewModel.getLogin() != null){
                    startActivity(Intent(this@OnBoardingActivity, MainActivity::class.java))
                    finish()
                } else{
                    startActivity(Intent(this@OnBoardingActivity, LoginActivity::class.java))
                    finish()
                }
            }
        }


    }
}