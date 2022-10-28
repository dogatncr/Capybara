package com.stdev.shopit.presentation.di

import com.example.capybara.data.repository.CapybaraRepositoryImpl
import com.example.capybara.data.repository.local.LocalDataSource
import com.example.capybara.data.repository.remote.RemoteDataSource
import com.example.capybara.domain.repository.CapybaraRepositoryInterface
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
    fun providesRepository(remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource) : CapybaraRepositoryInterface{
        return CapybaraRepositoryImpl(remoteDataSource,localDataSource)
    }

}