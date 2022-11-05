package com.example.capybara.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capybara.data.util.DataStoreManager
import com.example.capybara.presentation.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val dataStoreManager: DataStoreManager, private val firebaseAuth: FirebaseAuth) : ViewModel() {
    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Empty)
    val uiState: StateFlow<MainUiState> = _uiState

    private val _uiEvent = MutableSharedFlow<SplashViewEvent>(replay = 0)
    val uiEvent: SharedFlow<SplashViewEvent> = _uiEvent

    fun checkOnBoardingVisibleStatus() {
        viewModelScope.launch {
            val isOnBoardingVisible = dataStoreManager.getOnBoardingVisible.first()
            if (checkCurrentUser()) {
                start("main")
            } else {
                if (!isOnBoardingVisible) {
                    start("login")
                } else {
                    start("onboard")
                }
            }
        }
    }
    private fun checkCurrentUser(): Boolean {
        firebaseAuth.currentUser?.let {
            return true
        } ?: run {
            return false
        }
    }

    fun start( type:String) {
        viewModelScope.launch {
        _uiState.emit( MainUiState.Loading)
            if (type =="main") {
                _uiState.emit( MainUiState.Home)
            } else if(type =="login") {
                _uiState.emit( MainUiState.Login)
            }
            else{
                _uiState.emit( MainUiState.Onboard)
            }
    }
    }
    fun logout(){
        viewModelScope.launch {
        firebaseAuth.signOut()
        _uiState.emit( MainUiState.Login)
        }
    }
}

sealed class MainUiState() {
    object Home : MainUiState()
    object Login :MainUiState()
    object Onboard : MainUiState()

    object Empty : MainUiState()
    object Loading :MainUiState()
    object Error : MainUiState()
}
sealed class SplashViewEvent {
    object NavigateToOnBoarding : SplashViewEvent()
    class NavigateToMain(val isNavigateHome: Boolean) : SplashViewEvent()
}