package com.example.capybara.data.di
import android.app.Application
import androidx.room.Room
import com.example.capybara.data.database.CapybaraDAO
import com.example.capybara.data.database.CapybaraDatabase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(app : Application) : CapybaraDatabase{
        return Room.databaseBuilder(app,CapybaraDatabase::class.java,"capybara_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesDao(database: CapybaraDatabase) : CapybaraDAO{
        return database.capybaraDao()
    }

}