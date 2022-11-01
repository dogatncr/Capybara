package com.example.capybara.domain.usecases

import com.example.capybara.data.models.CategoryList
import com.example.capybara.data.models.Product
import com.example.capybara.data.models.ProductList
import com.example.capybara.data.util.DataState
import com.example.capybara.domain.repository.RemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class ProductUseCase @Inject constructor(
    private val repository: RemoteDataSource
) {

    suspend fun getAllProducts() : DataState<ProductList> {
        return repository.getAllProducts()
    }

    suspend fun getAllCategories() :ArrayList<String>{
        return repository.getAllCategories()
    }

    suspend fun getCategoryProducts(category : String) : ArrayList<Product> {
        return repository.getCategoryProducts(category)
    }

}