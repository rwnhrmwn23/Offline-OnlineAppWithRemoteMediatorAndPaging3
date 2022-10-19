package com.astro.test.irwan.core.domain.usecase

import androidx.paging.PagingData
import com.astro.test.irwan.core.domain.model.Github
import com.astro.test.irwan.core.domain.repository.AstroRepository
import kotlinx.coroutines.flow.Flow

class AstroInteractor(private val astroRepository: AstroRepository) : AstroUseCase {
    override fun users(
        username: String,
        isAsc: Boolean,
        page: Int,
        perPage: Int
    ): Flow<PagingData<Github>> =
        astroRepository.users(username, isAsc, page, perPage)

    override suspend fun updateFavorite(github: Github, state: Boolean) =
        astroRepository.updateFavorite(github, state)
}