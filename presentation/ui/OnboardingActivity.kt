package com.example.capybara.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.viewpager2.widget.ViewPager2
import com.example.capybara.R
import com.example.capybara.databinding.ActivityOnboardingBinding
import com.example.capybara.presentation.adapter.OnBoardingAdapter
import com.example.capybara.presentation.viewmodels.OnBoardingViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity: AppCompatActivity(){
    private val viewModel by viewModels<OnBoardingViewModel>()
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = OnBoardingAdapter(this, this)
        TabLayoutMediator(binding.pageIndicator, binding.viewPager) { tab, position ->
        }.attach()

        initViews()
    }
    private fun initViews() {
        binding.textSkip.setOnClickListener {
            finish()
            val intent =
                Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            //Animatoo.animateSlideLeft(this)
        }
        binding.btnNextStep.setOnClickListener{
            if (getItem() > binding.viewPager.childCount) {
                finish()
                val intent =
                    Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                //Animatoo.animateSlideLeft(this)
            } else {
                binding.viewPager.setCurrentItem(getItem() + 1, true)
            }
        }
    }
    private fun getItem(): Int {
        return binding.viewPager.currentItem
    }


}