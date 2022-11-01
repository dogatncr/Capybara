package com.example.capybara.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capybara.data.models.*
import com.example.capybara.data.util.DataState
import com.example.capybara.domain.repository.RemoteRepository
import com.example.capybara.domain.usecases.ProductUseCase
import com.example.capybara.domain.usecases.ProductUseCaseParams
import com.example.capybara.domain.usecases.ProductUseCaseState
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
    private val repository:RemoteRepository
) :
    ViewModel(){
    lateinit var products : ArrayList<Product>
    var categories : ArrayList<String> =ArrayList()
    var catList : ArrayList<Category> = ArrayList()

    private val _uiState = MutableStateFlow<HomeViewState>(HomeViewState.Empty)
    val uiState: StateFlow<HomeViewState> = _uiState

    private val _uiEvent = MutableSharedFlow<HomeViewEvent>(replay = 0)
    val uiEvent: SharedFlow<HomeViewEvent> = _uiEvent

    init {
        getCategoriesWithProducts()
    }
    fun getCategoriesWithProducts() = viewModelScope.launch {
        try {
            categories = repository.getAllCategories()
            productUseCase.invoke(ProductUseCaseParams(categories)).collect {
                    when (it) {
                        is ProductUseCaseState.Loading -> {}
                        is ProductUseCaseState.Error -> {
                            _uiEvent.emit(HomeViewEvent.ShowError(it.error.toString()))
                        }
                        is ProductUseCaseState.Success -> {
                            val res=it.data
                            _uiState.value = HomeViewState.Success(res)

                        }
                    }
                }



        }
        catch (e : Exception){
            _uiEvent.emit(HomeViewEvent.ShowError("Network Error"))
        }
    }
}

sealed class HomeViewEvent {
    data class ShowError(val message: String?) : HomeViewEvent()
}

sealed class HomeViewState {
    object Empty : HomeViewState()
    class Success(val categories: ArrayList<Category>?) : HomeViewState()
    object Loading : HomeViewState()
}