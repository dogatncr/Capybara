package com.example.capybara.data.repository

import com.example.capybara.data.api.ApiServices
import com.example.capybara.data.models.ProductList
import com.example.capybara.data.util.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiServices: ApiServices
) :BaseRemoteDataSource(), RemoteDataSource {
    override suspend fun getAllProducts(): Flow<DataState<ProductList>> {
        return getResult {apiServices.getAllProducts()}
    }

    override suspend fun getProduct(ItemId: Int): DataState<ProductList> {
        return apiServices.getProduct(ItemId)
    }

    override suspend fun getAllCategories(): ArrayList<String> {
        return apiServices.getAllCategories()
    }

    override suspend fun getCategoryProducts(category: String): Flow<DataState<ProductList>> {
        return getResult {apiServices.getCategoryProducts(category)}
    }

}