package com.example.capybara.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capybara.data.util.DataStoreManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel  @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<SignupViewEvent>(replay = 0)
    val uiEvent: SharedFlow<SignupViewEvent> = _uiEvent

    private val _uiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Empty)
    val uiState: StateFlow<SignUpUiState> = _uiState

    fun signup(email: String, password: String) {
        viewModelScope.launch {
            isValidFields(email, password)?.let {
                _uiEvent.emit(SignupViewEvent.ShowError(it))
            } ?: kotlin.run {
                _uiState.emit(SignUpUiState.Loading)
                firebaseAuth.createUserWithEmailAndPassword(
                    email,
                    password
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        setUser(email, task.result.user?.uid)

                    } else {
                        viewModelScope.launch {
                            _uiEvent.emit(SignupViewEvent.ShowError(task.exception?.message.toString()))
                            _uiState.emit(SignUpUiState.Error)
                        }
                    }
                }
            }
        }
    }

    private fun setUser(userName: String, uuid: String?) {
        viewModelScope.launch {

            val uname=userName.subSequence(0, userName.indexOf('@'))
            dataStoreManager.setUserName(uname as String)
            fireStore.collection("users").add(mapOf("username" to uname, "uuid" to uuid))
                .addOnSuccessListener { documentReference ->
                    viewModelScope.launch {
                        _uiState.emit(SignUpUiState.Success)
                        _uiEvent.emit(SignupViewEvent.NavigateToMain) }
                }.addOnFailureListener { error ->
                    viewModelScope.launch {
                        _uiState.emit(SignUpUiState.Error)
                        _uiEvent.emit(SignupViewEvent.ShowError(error.message.toString()))
                    }
                }
        }
    }

    private fun isValidFields(
        email: String,
        password: String,
    ): String? {
        fun isValidEmptyField() =
            email.isNotEmpty() && password.isNotEmpty()

        fun isValidPasswordLength() = password.length >= 6
        fun isValidEmail() = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

        if (isValidEmptyField().not()) {
            return "Please fill all fields"
        } else if (isValidEmail().not()) {
            return "Please enter a valid email address"
        } else if (isValidPasswordLength().not()) {
            return "Password must be at least 6 characters"
        }
        return null
    }
}
sealed class SignupViewEvent {
    object NavigateToMain : SignupViewEvent()
    class ShowError(val error: String) : SignupViewEvent()
}
sealed class SignUpUiState {
    object Empty : SignUpUiState()
    object Loading : SignUpUiState()
    object Success : SignUpUiState()
    object Error : SignUpUiState()
}