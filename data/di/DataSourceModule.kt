package com.stdev.shopit.presentation.di

import com.example.capybara.data.api.ApiServices
import com.example.capybara.data.database.CapybaraDAO
import com.example.capybara.data.repository.local.LocalDataSource
import com.example.capybara.data.repository.local.LocalDataSourceImpl
import com.example.capybara.data.repository.remote.RemoteDataSource
import com.example.capybara.data.repository.remote.RemoteDataSourceImpl

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    fun providesLocalDataSource(capybaraDAO: CapybaraDAO) : LocalDataSource {
        return LocalDataSourceImpl(capybaraDAO)
    }

    @Singleton
    @Provides
    fun provideShopRemoteDataSource(service: ApiServices) : RemoteDataSource {
        return RemoteDataSourceImpl(service)
    }

}