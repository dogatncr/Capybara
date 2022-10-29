package com.example.capybara.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import com.example.capybara.R
import dagger.hilt.android.AndroidEntryPoint
import android.content.SharedPreferences
import com.example.capybara.databinding.ActivityMainBinding


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Capybara)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}