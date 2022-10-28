package com.stdev.shopit.presentation.di

import com.example.capybara.data.api.ApiServices
import com.example.capybara.data.database.CapybaraDAO
import com.example.capybara.data.repository.LocalDataSourceImpl
import com.example.capybara.data.repository.RemoteDataSourceImpl
import com.example.capybara.domain.repository.LocalDataSource
import com.example.capybara.domain.repository.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesRemoteDataSource(apiServices: ApiServices) : RemoteDataSource{
        return RemoteDataSourceImpl(apiServices)
    }
    @Singleton
    @Provides
    fun providesLocalDataSource(dao: CapybaraDAO) :LocalDataSource{
        return LocalDataSourceImpl(dao)
    }

}