package com.stdev.shopit.presentation.di

import com.example.capybara.data.api.ApiServices
import com.example.capybara.data.database.CapybaraDAO
import com.example.capybara.domain.repository.LocalDataSource
import com.example.capybara.data.repository.LocalDataSourceImpl
import com.example.capybara.domain.repository.RemoteDataSource
import com.example.capybara.data.repository.RemoteDataSourceImpl

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