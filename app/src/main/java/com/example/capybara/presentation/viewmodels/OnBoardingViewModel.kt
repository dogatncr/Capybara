package com.example.capybara.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capybara.data.util.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(private val dataStoreManager: DataStoreManager) :
    ViewModel() {
    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

     fun setOnBoardingStatus() {
        viewModelScope.launch {
            dataStoreManager.setOnBoardingVisible(false)
        }
         _isLoading.value = false
    }
}