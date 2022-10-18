package com.astro.test.irwan.core.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.astro.test.irwan.core.data.paging.GithubRemoteMediator
import com.astro.test.irwan.core.data.source.local.LocalDataSource
import com.astro.test.irwan.core.data.source.local.entity.GithubEntity
import com.astro.test.irwan.core.data.source.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow

class AstroRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : AstroRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun users(username: String, page: Int, perPage: Int): Flow<PagingData<GithubEntity>> {
        return Pager(
            config = PagingConfig(10),
            remoteMediator = GithubRemoteMediator(username, remoteDataSource, localDataSource),
            pagingSourceFactory = {
                localDataSource.getUser(username)
            }
        ).flow
    }

}