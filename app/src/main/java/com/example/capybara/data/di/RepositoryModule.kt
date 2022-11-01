package com.example.capybara.data.di

import com.example.capybara.data.api.ApiServices
import com.example.capybara.data.database.CapybaraDAO
import com.example.capybara.data.repository.LocalDataSourceImpl
import com.example.capybara.data.repository.RemoteDataSourceImpl
import com.example.capybara.data.repository.LocalDataSource
import com.example.capybara.data.repository.RemoteDataSource
import com.example.capybara.domain.repository.RemoteRepository
import com.example.capybara.domain.repository.RemoteRepositoryImpl
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
    fun providesRemoteDataSource(apiServices: ApiServices) : RemoteDataSource {
        return RemoteDataSourceImpl(apiServices)
    }
    @Singleton
    @Provides
    fun providesLocalDataSource(dao: CapybaraDAO) : LocalDataSource {
        return LocalDataSourceImpl(dao)
    }
    @Singleton
    @Provides
    fun provideRemoteRepository(remoteDataSource: RemoteDataSource): RemoteRepository =
        RemoteRepositoryImpl(remoteDataSource)
}