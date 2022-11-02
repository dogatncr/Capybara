package com.example.capybara.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.capybara.data.models.CartProduct
import com.example.capybara.data.models.Category
import com.example.capybara.domain.usecases.CartUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartUseCase: CartUseCase
) : ViewModel() {

    val totalItems : MutableLiveData<Int> = MutableLiveData()
    val totalItemsPrice : MutableLiveData<Double> = MutableLiveData()

    fun getCartItems() = liveData {
        cartUseCase.getCartItems().collect{
            emit(it)
            computeTotal(it)
        }
    }

    fun computeTotal(cartItems : List<CartProduct>) = viewModelScope.launch(Dispatchers.IO){
        var price = 0.00
        cartItems.forEach { product ->
            price += product.price.toDouble()
        }
        totalItemsPrice.postValue(price)
        totalItems.postValue(cartItems.size)
    }

    fun deleteCartItem(CartProduct: CartProduct) = viewModelScope.launch(Dispatchers.IO) {
        cartUseCase.deleteCartItem(CartProduct)
    }

    fun clearCart() = viewModelScope.launch(Dispatchers.IO) {
        cartUseCase.clearCart()
    }

    fun incrementItem(cartItem: CartProduct){
        updateProductInCart(quantity = cartItem.quantity + 1,cartItem)
    }

    fun decrementItem(cartItem: CartProduct){
        updateProductInCart(quantity = cartItem.quantity -1,cartItem)
    }

    private fun updateProductInCart(quantity: Int,cartItem: CartProduct) = viewModelScope.launch(
        Dispatchers.IO
    ){
        if(quantity>1){
        val copy = cartItem.copy(cartItem.id,cartItem.image,cartItem.price,cartItem.title, quantity = quantity)
        cartUseCase.updateCartItem(copy)}
        else{
            cartUseCase.deleteCartItem(cartItem)
        }
    }
}