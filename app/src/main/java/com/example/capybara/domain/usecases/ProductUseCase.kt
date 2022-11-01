package com.example.capybara.domain.usecases

import com.example.capybara.data.models.Category
import com.example.capybara.data.util.DataState
import com.example.capybara.domain.repository.RemoteRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProductUseCase @Inject constructor(
    private val repository: RemoteRepository
) :BaseUseCase<ProductUseCaseParams,ProductUseCaseState> {
    var catList : ArrayList<Category> = ArrayList()
    override fun invoke(params: ProductUseCaseParams): Flow<ProductUseCaseState> {
        return flow {
            emit(ProductUseCaseState.Loading)
            getProds(params.categories).collect {
                emit(it)
            }

        }
    }
    private fun getProds(categories :ArrayList<String>)= callbackFlow{
        for(each in categories){
           repository.getCategoryProducts(each).collect{data->
               when(data){
                   is DataState.Error -> trySendBlocking(ProductUseCaseState.Error(data.error?.status_message))
                   is DataState.Loading -> trySendBlocking(ProductUseCaseState.Loading)
                   is DataState.Success -> catList.add(Category(each,data.data))
               }
           }
        }
        trySendBlocking(ProductUseCaseState.Success(catList))
        awaitClose { channel.close() }
    }
}
data class ProductUseCaseParams(
    val categories: ArrayList<String>,
)

sealed class ProductUseCaseState {
    data class Success(val data: ArrayList<Category>) : ProductUseCaseState()
    data class Error(val error: String?) : ProductUseCaseState()
    object Loading : ProductUseCaseState()
}