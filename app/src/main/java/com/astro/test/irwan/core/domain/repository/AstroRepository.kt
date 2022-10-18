package com.astro.test.irwan.core.domain.repository

import androidx.paging.PagingData
import com.astro.test.irwan.core.data.source.local.entity.GithubEntity
import kotlinx.coroutines.flow.Flow

interface AstroRepository {
    fun users(username: String, page: Int, perPage: Int): Flow<PagingData<GithubEntity>>
}