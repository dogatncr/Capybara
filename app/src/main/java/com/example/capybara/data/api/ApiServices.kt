package com.example.capybara.data.api

import com.example.capybara.data.models.CategoryList
import com.example.capybara.data.models.Product
import com.example.capybara.data.models.ProductList
import com.example.capybara.data.util.DataState
import retrofit2.http.*

interface ApiServices {
    //Get all products
    @GET("/products")
    suspend fun getAllProducts() : DataState<ProductList>
    //Get a single product
    @GET("products/{id}")
    suspend fun getProduct(@Path(value = "id") ItemId : Int) : DataState<ProductList>

    //Get all categories
    @GET("products/categories")
    suspend fun getAllCategories() : ArrayList<String>
    //Get all products from category
    @GET("products/category/{category}")
    suspend fun getCategoryProducts(@Path(value = "category") category : String) : ArrayList<Product>


}