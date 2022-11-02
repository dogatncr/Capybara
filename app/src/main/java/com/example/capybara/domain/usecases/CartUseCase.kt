package com.example.capybara.domain.usecases

import com.example.capybara.data.models.CartProduct
import com.example.capybara.data.repository.LocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartUseCase @Inject constructor(
    private val source: LocalDataSource
) {

    suspend fun deleteCartItem(cartProduct: CartProduct){
        source.deleteCartItems(cartProduct)
    }

    suspend fun clearCart(){
        source.clearCart()
    }

    fun getCartItems() : Flow<List<CartProduct>> {
        return source.getCartItems()
    }

    suspend fun addToCartItem(cartProduct: CartProduct){
        source.addToCart(cartProduct)
    }

    suspend fun updateCartItem(cartProduct: CartProduct){
        source.updateCartItems(cartProduct)
    }

}