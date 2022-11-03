package com.example.capybara.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capybara.data.models.CartProduct
import com.example.capybara.data.models.Category
import com.example.capybara.domain.usecases.CartUseCase
import com.example.capybara.domain.usecases.ProductUseCaseParams
import com.example.capybara.domain.usecases.ProductUseCaseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val cartUseCase: CartUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<DetailViewState>(DetailViewState.Loading)
    val uiState: StateFlow<DetailViewState> = _uiState


    fun getCartItem(itemId:Int) = viewModelScope.launch {
        try {
            cartUseCase.getCartItem(itemId).collect{
                _uiState.emit(DetailViewState.CartContains( quantity = it.quantity))
            }
        }
        catch (e : Exception){
            _uiState.emit(DetailViewState.CartNotContains)
        }
    }

    fun saveToCart(cartItem: CartProduct) = viewModelScope.launch(Dispatchers.IO) {
        cartUseCase.addToCartItem(cartItem)
        getCartItem(cartItem.id)
    }

    fun updateProductInCart(incremenet:Boolean ,cartItem: CartProduct) = viewModelScope.launch(
        Dispatchers.IO
    ){
        if(incremenet){
            val copy = cartItem.copy(cartItem.id,cartItem.image,cartItem.price,cartItem.title,cartItem.quantity)
            cartUseCase.updateCartItem(copy)}
        else{
            if(cartItem.quantity >1){
                val copy = cartItem.copy(cartItem.id,cartItem.image,cartItem.price,cartItem.title, quantity = cartItem.quantity-1)
                cartUseCase.updateCartItem(copy)}
            else{
                cartUseCase.deleteCartItem(cartItem)}
        }
        getCartItem(cartItem.id)
    }
}
sealed class DetailViewState {
    class CartContains (val quantity:Int) : DetailViewState()
    object CartNotContains : DetailViewState()
    object Loading : DetailViewState()
    object Error : DetailViewState()
}