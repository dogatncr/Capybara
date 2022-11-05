package com.example.capybara.presentation.viewmodels

import android.provider.ContactsContract.Data
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
    //shared viewmodel for both home and search operations
    var categories : ArrayList<String> =ArrayList()

    private val _uiState = MutableStateFlow<HomeViewState>(HomeViewState.Empty)
    val uiState: StateFlow<HomeViewState> = _uiState

    private val _uiEvent = MutableSharedFlow<HomeViewEvent>(replay = 0)
    val uiEvent: SharedFlow<HomeViewEvent> = _uiEvent

    private val _searchState = MutableSharedFlow<SearchViewState>(replay = 0)
    val searchState: SharedFlow<SearchViewState> = _searchState

    init {
        getCategoriesWithProducts()
    }
    fun getCategoriesWithProducts() = viewModelScope.launch {
        try {
            categories = repository.getAllCategories()
            productUseCase.invoke(ProductUseCaseParams(categories)).collect {
                    when (it) {
                        is ProductUseCaseState.Loading -> {_uiState.emit(HomeViewState.Loading)}
                        is ProductUseCaseState.Error -> {
                            _uiEvent.emit(HomeViewEvent.ShowError(it.error.toString()))
                        }
                        is ProductUseCaseState.Success -> {
                            val res=it.data
                            _uiState.emit( HomeViewState.Success(res))

                        }
                    }
                }



        }
        catch (e : Exception){
            _uiEvent.emit(HomeViewEvent.ShowError("Network Error"))
        }
    }
    val searchCategories : MutableLiveData<DataState<ArrayList<String>>> = MutableLiveData()

    fun searchAllCategories() = viewModelScope.launch(Dispatchers.IO) {
        try {
                val apiResult = repository.getAllCategories()
                searchCategories.postValue(DataState.Success(apiResult))
        }catch (e : Exception){
            searchCategories.postValue(DataState.Error(ApiError(401,"${e.localizedMessage} ?: Unknown Error") ))
        }
    }

    fun searchAllProducts() = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.getAllProducts().collect{
                when (it) {
                    is DataState.Error -> _searchState.emit(SearchViewState.Error(it.error))
                    is DataState.Loading -> _searchState.emit(SearchViewState.Loading)
                    is DataState.Success -> _searchState.emit(SearchViewState.Success(it.data))
                }
            }
        }catch (e : Exception){
            _searchState.emit(SearchViewState.Error(ApiError(401,"unkown error")))
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
sealed class SearchViewState {
    object Empty : SearchViewState()
    class Success(val products: ArrayList<Product>?) : SearchViewState()
    class Error(val message: ApiError?) : SearchViewState()
    object Loading : SearchViewState()
}