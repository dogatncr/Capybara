package com.example.capybara.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capybara.data.util.DataStoreManager
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class SplashViewModel @Inject constructor(private val dataStoreManager: DataStoreManager, private val firebaseAuth: FirebaseAuth) :
    ViewModel() {

    private val _uiEvent = MutableSharedFlow<SplashViewEvent>(replay = 0)
    val uiEvent: SharedFlow<SplashViewEvent> = _uiEvent

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    // Checking Login User
    // If user is logged in, navigate to main activity
    // If user is not logged in, navigate to login activity

    init {
        checkOnBoardingVisibleStatus()
    }

    private fun checkOnBoardingVisibleStatus() {
        viewModelScope.launch {
            val isOnBoardingVisible = dataStoreManager.getOnBoardingVisible.first()
            if (checkCurrentUser()) {
                _uiEvent.emit(SplashViewEvent.NavigateToMain(true))
            } else {
                if (!isOnBoardingVisible) {
                    _uiEvent.emit(SplashViewEvent.NavigateToMain(false))
                } else {
                    _uiEvent.emit(SplashViewEvent.NavigateToOnBoarding)
                }
            }
            _isLoading.value = false
        }
    }
    private fun checkCurrentUser(): Boolean {
        firebaseAuth.currentUser?.let {
            return true
        } ?: run {
            return false
        }
    }
}

sealed class SplashViewEvent {
    object NavigateToOnBoarding : SplashViewEvent()
    class NavigateToMain(val isNavigateHome: Boolean) : SplashViewEvent()
}