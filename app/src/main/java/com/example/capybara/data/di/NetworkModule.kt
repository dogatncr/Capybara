package com.stdev.shopit.presentation.di

import com.example.capybara.data.api.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl: String,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    fun provideBaseUrl(): String {
        return "https://https://fakestoreapi.com/"
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }
    @Provides
    fun provideApiService(retrofit: Retrofit) : ApiServices {
        return retrofit.create(ApiServices::class.java)
    }

}