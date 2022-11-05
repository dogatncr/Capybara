package com.example.capybara.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import com.example.capybara.R
import dagger.hilt.android.AndroidEntryPoint

import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.ui.setupWithNavController
import com.example.capybara.databinding.ActivityMainBinding
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
        viewModel.checkOnBoardingVisibleStatus()
        lifecycleScope.launchWhenResumed {
                viewModel.uiState.collect {
                    when (it) {
                        is MainUiState.Loading ->binding.loadingPanel.visibility= VISIBLE
                        MainUiState.Empty ->  Log.d("main","activity started")
                        MainUiState.Error -> Log.d("error","error in main activity")
                        MainUiState.Home ->  initNavController(true)
                        MainUiState.Login ->  initNavController(false)
                        MainUiState.Onboard -> navigateToOnBoarding()
                    }
                }
            }
    }
   /* private fun initLogin() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        navController.navigate(R.id.login_graph)
        binding.isVisibleBar = false
    }*/
    private fun initNavController(isNavHome:Boolean) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
       binding.bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.productDetailFragment || destination.id == R.id.cartFragment || destination.id == R.id.loginFragment || destination.id == R.id.signupFragment ) {
                binding.isVisibleBar = false
                binding.cartButton.visibility= GONE
            } else if(destination.id == R.id.homeFragment){
                binding.cartButton.visibility= VISIBLE
                binding.cartButton.setOnClickListener{
                    navController.navigate(R.id.action_homeFragment_to_cartFragment)
                    }
                binding.isVisibleBar = true
                }
            else{
                binding.cartButton.visibility= GONE
                binding.isVisibleBar = true}
        }

       if (isNavHome.not()) {
           navController.navigate(R.id.login_graph)
       }
       binding.isVisibleBar = isNavHome
    }
    private fun navigateToOnBoarding() {
        lifecycleScope.launch {
            val intent = Intent(this@MainActivity, OnboardingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent)
            finish()
        }
    }

}