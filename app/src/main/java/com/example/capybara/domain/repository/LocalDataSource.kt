package com.example.capybara.domain.repository

import com.example.capybara.data.models.CartProduct
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun addToCart(product: CartProduct)
    fun getCartItems() : Flow<List<CartProduct>>
    suspend fun updateCartItems(product: CartProduct)
    suspend fun deleteCartItems(product: CartProduct)
    suspend fun clearCart()

}