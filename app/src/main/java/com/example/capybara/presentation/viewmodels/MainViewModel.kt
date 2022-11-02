package com.example.capybara.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capybara.presentation.ui.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
    val st=savedStateHandle
    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Empty)
    val uiState: StateFlow<MainUiState> = _uiState

    fun start() {
        viewModelScope.launch {
        _uiState.emit( MainUiState.Loading)
        st.get<Boolean>(MainActivity.KEY_NAVIGATE_HOME)?.let {
            if (it) {
                _uiState.emit( MainUiState.Success(true))
            } else {
                _uiState.emit( MainUiState.Success(false))
            }
        }
    }
    }
}

sealed class MainUiState() {
    class Success(val isNavigateHome: Boolean = false) : MainUiState()
    object Empty : MainUiState()
    object Loading :MainUiState()
    object Error : MainUiState()
}