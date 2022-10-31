package com.example.capybara.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import com.example.capybara.R
import dagger.hilt.android.AndroidEntryPoint

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.setupWithNavController
import com.example.capybara.databinding.ActivityMainBinding
import com.example.capybara.databinding.ActivityOnboardingBinding
import com.example.capybara.presentation.viewmodels.MainUiState
import com.example.capybara.presentation.viewmodels.MainViewModel
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    companion object {
        const val KEY_NAVIGATE_HOME = "KEY_NAVIGATE_HOME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenResumed {
            launch {
                viewModel.uiState.collect {
                    when (it) {
                        is MainUiState.Success -> {
                            initNavController(it.isNavigateHome)
                        }
                    }
                }
            }
        }
    }
    private fun initNavController(isNavigateToHome: Boolean) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView
        binding.bottomNavigationView.setupWithNavController(navController)
        if (isNavigateToHome.not()) {
            navController.navigate(R.id.login_graph)
        }
        binding.isVisibleBar = isNavigateToHome

    }
}