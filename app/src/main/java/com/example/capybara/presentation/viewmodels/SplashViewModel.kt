package com.example.capybara.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading
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
                if (!it) {
                    _uiEvent.emit(SplashViewEvent.NavigateToMain)
                    // Navigate to main activity
                } else {
                    _uiEvent.emit(SplashViewEvent.NavigateToOnBoarding)
                    // Navigate to on boarding activity
                }
            }
            _isLoading.value = false
        }
    }
}

sealed class SplashViewEvent {
    object NavigateToLogin : SplashViewEvent()
    object NavigateToOnBoarding : SplashViewEvent()
    object NavigateToMain : SplashViewEvent()
}