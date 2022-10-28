package com.example.capybara.data.repository.remote

import com.example.capybara.data.api.ApiServices
import com.example.capybara.data.models.Product
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiServices: ApiServices
) : RemoteDataSource {
    override suspend fun getAllProducts(): Response<List<Product>> {
        return apiServices.getAllProducts()
    }

    override suspend fun getProduct(ItemId: Int): Response<Product> {
        return apiServices.getProduct(ItemId)
    }

    override suspend fun getAllCategories(): Response<List<String>> {
        return apiServices.getAllCategories()
    }

    override suspend fun getCategoryProducts(category: String): Response<List<Product>> {
        return apiServices.getCategoryProducts(category)
    }

}