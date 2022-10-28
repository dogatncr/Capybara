package com.example.capybara.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.capybara.data.models.CartProduct

@Database(entities = [CartProduct::class], version = 3, exportSchema = false)
abstract class CapybaraDatabase : RoomDatabase(){

    abstract fun capybaraDao() : CapybaraDAO

}