package com.example.capybara.domain.repository

import com.example.capybara.data.models.CategoryList
import com.example.capybara.data.models.ProductList
import com.example.capybara.data.repository.RemoteDataSource
import com.example.capybara.data.util.DataState
import retrofit2.Response
import retrofit2.http.Path
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteRepositoryImpl  @Inject constructor(private val remoteDataSource: RemoteDataSource) :RemoteRepository {

    override suspend fun getAllCategories() : ArrayList<String>{
        return remoteDataSource.getAllCategories()
    }

    override suspend fun getCategoryProducts(category: String): Flow<DataState<ProductList>> {
        return remoteDataSource.getCategoryProducts(category)
    }

    override suspend fun getAllProducts(): Flow<DataState<ProductList>> {
        return remoteDataSource.getAllProducts()
    }

}