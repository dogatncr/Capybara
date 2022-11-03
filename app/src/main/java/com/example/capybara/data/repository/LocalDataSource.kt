package com.example.capybara.data.repository

import com.example.capybara.data.models.CartProduct
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun addToCart(product: CartProduct)
    fun getCartItems() : Flow<List<CartProduct>>
    fun getCartItem(productId: Int):Flow <CartProduct>
    suspend fun updateCartItems(product: CartProduct)
    suspend fun deleteCartItems(product: CartProduct)
    suspend fun clearCart()
}