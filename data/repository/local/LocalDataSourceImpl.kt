package com.example.capybara.data.repository.local

import com.example.capybara.data.database.CapybaraDAO
import com.example.capybara.data.models.CartProduct
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val DAO: CapybaraDAO
)  : LocalDataSource {
    override suspend fun addToCart(product: CartProduct) {
        return DAO.addToCart(product)
    }

    override fun getCartItems(): Flow<List<CartProduct>> {
        return DAO.cartItems()
    }

    override suspend fun updateCartItems(product: CartProduct) {
        return DAO.updateCart(product)
    }

    override suspend fun deleteCartItems(product: CartProduct) {
        return DAO.deleteCart(product)
    }

    override suspend fun clearCart() {
        return DAO.clearAll()
    }


}