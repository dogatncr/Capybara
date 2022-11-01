package com.example.capybara.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capybara.data.models.*
import com.example.capybara.data.util.DataState
import com.example.capybara.domain.repository.RemoteDataSource
import com.example.capybara.domain.usecases.ProductUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productUseCase: ProductUseCase
) :
    ViewModel(){
    lateinit var products : ArrayList<Product>
    lateinit var categories : ArrayList<String>
    var catList : ArrayList<Category> =ArrayList()

    val categoryList: MutableLiveData<DataState<ArrayList<Category>>> = MutableLiveData()

    fun getCategoriesWithProducts() = viewModelScope.launch(Dispatchers.IO) {
        categoryList.postValue(DataState.Loading())
        try {
            categories = productUseCase.getAllCategories()
            for(each in categories) {
                products=productUseCase.getCategoryProducts(each)
                catList.add(Category(each,products))
            }
            categoryList.postValue(DataState.Success(catList))
        }catch (e : Exception){
            categoryList.postValue(DataState.Error(ApiError(401,"Unknown error")))
        }
    }
}