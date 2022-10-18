package com.astro.test.irwan.core.domain.usecase

import com.astro.test.irwan.core.domain.repository.AstroRepository

class AstroInteractor(private val astroRepository: AstroRepository) : AstroUseCase {
    override fun users(username: String, page: Int, perPage: Int) =
        astroRepository.users(username, page, perPage)
}