package com.example.capybara.domain.usecases

import kotlinx.coroutines.flow.Flow

interface BaseUseCase<in Params, ReturnType : Any?> {
    fun invoke(params: Params): Flow<ReturnType>
}