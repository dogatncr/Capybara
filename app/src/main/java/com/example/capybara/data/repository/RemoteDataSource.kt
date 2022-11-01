package com.example.capybara.data.repository

import com.example.capybara.data.models.CategoryList
import com.example.capybara.data.models.Product
import com.example.capybara.data.models.ProductList
import com.example.capybara.data.util.DataState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getAllProducts() : DataState<ProductList>
    suspend fun getProduct(ItemId : Int) : DataState<ProductList>
    suspend fun getAllCategories() : ArrayList<String>
    suspend fun getCategoryProducts(category : String) : Flow<DataState<ProductList>>
}