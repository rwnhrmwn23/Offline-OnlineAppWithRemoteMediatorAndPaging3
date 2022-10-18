package com.astro.test.irwan.core.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.astro.test.irwan.core.data.source.local.LocalDataSource
import com.astro.test.irwan.core.data.source.local.entity.GithubEntity
import com.astro.test.irwan.core.data.source.local.entity.RemoteKeysEntity
import com.astro.test.irwan.core.data.source.remote.RemoteDataSource
import com.astro.test.irwan.core.data.source.remote.network.ApiResponse
import com.astro.test.irwan.utils.Mapper.mapToEntities

@OptIn(ExperimentalPagingApi::class)
class GithubRemoteMediator(
    private val username: String,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : RemoteMediator<Int, GithubEntity>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GithubEntity>
    ): MediatorResult {
        var data: List<GithubEntity>? = null

        val page = when (loadType) {
            LoadType.REFRESH ->{
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        return try {
            val responseData = remoteDataSource.users(username, page, state.config.pageSize)
            responseData.collect {
                data = when (it) {
                    is ApiResponse.Success -> {
                        it.data.mapToEntities()
                    }
                    is ApiResponse.Error -> {
                        null
                    }
                    else -> {
                        null
                    }
                }
            }
            val endOfPaginationReached = data?.isEmpty() == true

            if (loadType == LoadType.REFRESH) {
                localDataSource.deleteUsers()
                localDataSource.deleteRemoteKeys()
            }
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1
            val keys = data?.map {
                RemoteKeysEntity(id = it.id, prevKey = prevKey, nextKey = nextKey)
            }

            data?.let {
                localDataSource.insertUsers(it)
            }

            keys?.let {
                localDataSource.insertAll(keys)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, GithubEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            localDataSource.getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, GithubEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            localDataSource.getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, GithubEntity>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                localDataSource.getRemoteKeysId(id)
            }
        }
    }


}