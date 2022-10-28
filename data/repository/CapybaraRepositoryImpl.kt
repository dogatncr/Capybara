package com.example.capybara.data.repository

import com.example.capybara.data.models.CartProduct
import com.example.capybara.data.models.Product
import com.example.capybara.data.repository.local.LocalDataSource
import com.example.capybara.data.repository.remote.RemoteDataSource
import com.example.capybara.domain.repository.CapybaraRepositoryInterface
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class CapybaraRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : CapybaraRepositoryInterface {
    override suspend fun getAllProducts(): Response<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProduct(ItemId: Int): Response<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCategories(): Response<List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCategoryProducts(category: String): Response<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun addToCart(product: CartProduct) {
        TODO("Not yet implemented")
    }

    override fun getCartItems(): Flow<List<CartProduct>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateCartItems(product: CartProduct) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCartItems(product: CartProduct) {
        TODO("Not yet implemented")
    }

    override suspend fun clearCart() {
        TODO("Not yet implemented")
    }

}