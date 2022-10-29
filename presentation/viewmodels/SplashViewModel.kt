package com.example.capybara.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capybara.data.util.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class SplashViewModel @Inject constructor(private val dataStoreManager: DataStoreManager) :
    ViewModel() {

    private val _uiEvent = MutableSharedFlow<SplashViewEvent>(replay = 0)
    val uiEvent: SharedFlow<SplashViewEvent> = _uiEvent

    //TODO
    // Check Login User
    // If user is logged in, navigate to main activity
    // If user is not logged in, navigate to login activity

    init {
        checkOnBoardingVisibleStatus()
    }

    private fun checkOnBoardingVisibleStatus() {
        viewModelScope.launch {
            dataStoreManager.getOnBoardingVisible.collect {
                if (it) {
                    _uiEvent.emit(SplashViewEvent.NavigateToMain)
                    // Navigate to main activity
                } else {
                    _uiEvent.emit(SplashViewEvent.NavigateToOnBoarding)
                    // Navigate to on boarding activity
                }
            }
        }
    }
}

sealed class SplashViewEvent {
    object NavigateToLogin : SplashViewEvent()
    object NavigateToOnBoarding : SplashViewEvent()
    object NavigateToMain : SplashViewEvent()
}