package com.example.capybara.data.util

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreference @Inject constructor(
    private val sharedPreferences : SharedPreferences
) {
    fun isFirstAppLaunch(): Boolean {
        return sharedPreferences.getBoolean(Constants.FIRST_LAUNCH, true)
    }
}