package com.example.capybara.data.di

import android.content.Context
import android.content.SharedPreferences
import com.example.capybara.data.util.DataStoreManager
import com.example.capybara.presentation.adapter.CartAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideDataStoreManager(@ApplicationContext appContext: Context): DataStoreManager =
        DataStoreManager(appContext)

    @Singleton
    @Provides
    fun providesCartAdapter() : CartAdapter {
        return CartAdapter()
    }

}