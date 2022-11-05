package com.example.capybara

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capybara.data.util.DataStoreManager
import com.example.capybara.domain.usecases.LoginUseCase
import com.example.capybara.domain.usecases.LoginUseCaseParams
import com.example.capybara.domain.usecases.LoginUseCaseState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel( private val loginUseCase: LoginUseCase,
                      private val dataStoreManager: DataStoreManager,) : ViewModel(){

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Empty)
    val uiState: StateFlow<LoginUiState> = _uiState

    private val _uiEvent = MutableSharedFlow<LoginViewEvent>(replay = 0)
    val uiEvent: SharedFlow<LoginViewEvent> = _uiEvent
    fun getAuth() = FirebaseAuth.getInstance()

    fun onLoginClick(email:String,password:String) {
        viewModelScope.launch {
            if (isValidFields(email, password)) {
                loginUseCase.invoke(LoginUseCaseParams(email, password)).collect {
                    when (it) {
                        is LoginUseCaseState.Loading -> {
                            _uiState.emit(LoginUiState.Loading)
                        }
                        is LoginUseCaseState.Error -> {
                            _uiEvent.emit(LoginViewEvent.ShowError(it.error.toString()))
                            _uiState.emit(LoginUiState.Error)
                        }
                        is LoginUseCaseState.Success -> {
                            _uiEvent.emit(LoginViewEvent.NavigateToMain)
                            _uiState.emit(LoginUiState.Success)
                        }
                    }
                }
            } else {
                _uiEvent.emit(LoginViewEvent.ShowError("Please fill all fields"))
            }
        }
    }
    private fun isValidFields(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

}
sealed class LoginViewEvent {
    object NavigateToMain : LoginViewEvent()
    class ShowError(val error: String) : LoginViewEvent()
}

open class LoginUiState {
    object Empty : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    object Error : LoginUiState()
}