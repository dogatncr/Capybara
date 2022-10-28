package com.example.capybara.data.repository.remote

import com.example.capybara.data.models.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteDataSource {
    suspend fun getAllProducts() : Response<List<Product>>
    suspend fun getProduct(ItemId : Int) : Response<Product>
    suspend fun getAllCategories() : Response<List<String>>
    suspend fun getCategoryProducts(category : String) : Response<List<Product>>
}