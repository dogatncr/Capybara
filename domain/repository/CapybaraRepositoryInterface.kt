package com.example.capybara.domain.repository

import com.example.capybara.data.models.CartProduct
import com.example.capybara.data.models.Product
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CapybaraRepositoryInterface {
    suspend fun getAllProducts() : Response<List<Product>>
    suspend fun getProduct(ItemId : Int) : Response<Product>
    suspend fun getAllCategories() : Response<List<String>>
    suspend fun getCategoryProducts(category : String) : Response<List<Product>>

    suspend fun addToCart(product: CartProduct)
    fun getCartItems() : Flow<List<CartProduct>>
    suspend fun updateCartItems(product: CartProduct)
    suspend fun deleteCartItems(product: CartProduct)
    suspend fun clearCart()

}