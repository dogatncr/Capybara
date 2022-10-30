package com.example.capybara.presentation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.capybara.R
import com.example.capybara.presentation.viewmodels.SplashViewEvent
import com.example.capybara.presentation.viewmodels.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val viewModel by viewModels<SplashViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        installSplashScreen().setKeepOnScreenCondition {
            !viewModel.isLoading.value
        }
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenCreated {
            launch {
                viewModel.uiEvent.collect {
                    when (it) {
                        is SplashViewEvent.NavigateToOnBoarding -> {
                            navigateToOnBoarding()
                        }
                        is SplashViewEvent.NavigateToMain -> {
                            navigateToMain()
                        }
                        is SplashViewEvent.NavigateToLogin -> {

                        }
                    }
                }
            }
        }
    }

    private fun navigateToMain() {
        lifecycleScope.launch {
            delay(2000)
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent)
            finish()
        }
    }

    private fun navigateToOnBoarding() {
        lifecycleScope.launch {
            delay(2000)
            val intent = Intent(this@SplashActivity, OnboardingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent)
            finish()
        }
    }
}