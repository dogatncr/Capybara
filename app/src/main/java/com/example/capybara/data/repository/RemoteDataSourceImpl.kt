package com.example.capybara.data.repository

import com.example.capybara.data.api.ApiServices
import com.example.capybara.data.models.CategoryList
import com.example.capybara.data.models.Product
import com.example.capybara.data.models.ProductList
import com.example.capybara.data.util.DataState
import com.example.capybara.domain.repository.RemoteDataSource
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiServices: ApiServices
) : RemoteDataSource {
    override suspend fun getAllProducts(): DataState<ProductList> {
        return apiServices.getAllProducts()
    }

    override suspend fun getProduct(ItemId: Int): DataState<ProductList> {
        return apiServices.getProduct(ItemId)
    }

    override suspend fun getAllCategories(): ArrayList<String> {
        return apiServices.getAllCategories()
    }

    override suspend fun getCategoryProducts(category: String): ArrayList<Product> {
        return apiServices.getCategoryProducts(category)
    }

}