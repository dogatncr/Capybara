package com.example.capybara.data.api

import com.example.capybara.data.models.Product
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {
    //Get all products
    @GET("/products")
    suspend fun getAllProducts() : Response<List<Product>>
    //Get a single product
    @GET("products/{id}")
    suspend fun getProduct(@Path(value = "id") ItemId : Int) : Response<Product>
    //Get all categories
    @GET("products/categories")
    suspend fun getAllCategories() : Response<List<String>>
    //Get all products from category
    @GET("products/category/{category}")
    suspend fun getCategoryProducts(@Path(value = "category") category : String) : Response<List<Product>>


}