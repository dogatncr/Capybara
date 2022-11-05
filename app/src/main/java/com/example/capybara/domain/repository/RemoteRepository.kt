package com.example.capybara.domain.repository

import com.example.capybara.data.models.CategoryList
import com.example.capybara.data.models.ProductList
import com.example.capybara.data.util.DataState
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
    suspend fun getAllCategories() : ArrayList<String>

    suspend fun getCategoryProducts(category : String) : Flow<DataState<ProductList>>

    suspend fun getAllProducts() : Flow<DataState<ProductList>>
}