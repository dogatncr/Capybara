package com.example.capybara.data.repository

import com.example.capybara.data.repository.local.LocalDataSource
import com.example.capybara.data.repository.remote.RemoteDataSource
import com.example.capybara.domain.repository.CapybaraRepositoryInterface
import javax.inject.Inject

class CapybaraRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : CapybaraRepositoryInterface {

}