package com.astro.test.irwan.core.domain.usecase

import androidx.paging.PagingData
import com.astro.test.irwan.core.domain.model.Github
import kotlinx.coroutines.flow.Flow

interface AstroUseCase {
    fun users(
        username: String,
        isAsc: Boolean,
        page: Int,
        perPage: Int
    ): Flow<PagingData<Github>>

    suspend fun updateFavorite(github: Github, state: Boolean)
}