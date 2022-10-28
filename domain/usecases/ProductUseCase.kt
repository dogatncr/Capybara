package com.example.capybara.domain.usecases

import com.example.capybara.data.models.Product
import com.example.capybara.domain.repository.CapybaraRepositoryInterface
import retrofit2.Response
import javax.inject.Inject

class ProductUseCase @Inject constructor(
    private val repository: CapybaraRepositoryInterface
) {

    suspend fun getAllProducts() : Response<List<Product>> {
        return repository.getAllProducts()
    }

    suspend fun getAllCategories() :Response<List<String>>{
        return repository.getAllCategories()
    }

    suspend fun getCategoryProducts(category : String) : Response<List<Product>>{
        return repository.getCategoryProducts(category)
    }

}