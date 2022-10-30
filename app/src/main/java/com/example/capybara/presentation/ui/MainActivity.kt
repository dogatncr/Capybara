package com.example.capybara.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import com.example.capybara.R
import dagger.hilt.android.AndroidEntryPoint
import android.content.SharedPreferences
import androidx.activity.viewModels
import com.example.capybara.databinding.ActivityMainBinding
import com.example.capybara.presentation.viewmodels.OnBoardingViewModel
import com.example.capybara.presentation.viewmodels.SplashViewModel


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<OnBoardingViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            !viewModel.isLoading.value
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}