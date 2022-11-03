package com.example.capybara.data.database

import androidx.room.*
import com.example.capybara.data.models.CartProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface CapybaraDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(product: CartProduct)

    @Update
    suspend fun updateCart(product: CartProduct)

    @Delete
    suspend fun deleteCart(product: CartProduct)

    @Query("select * from cart")
    fun cartItems() : Flow<List<CartProduct>>

    @Query("select * from cart WHERE id= :productId")
    fun getCartItem(productId: Int) : Flow<CartProduct>

    @Query("delete from cart")
    suspend fun clearAll()

}