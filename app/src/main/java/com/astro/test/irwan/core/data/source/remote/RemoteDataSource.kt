package com.astro.test.irwan.core.data.source.remote

import com.astro.test.irwan.core.data.source.remote.network.ApiResponse
import com.astro.test.irwan.core.data.source.remote.network.ApiService
import com.astro.test.irwan.core.domain.model.Github
import com.astro.test.irwan.utils.Mapper.mapToGithub
import com.astro.test.irwan.utils.asFlowStateEvent
import kotlinx.coroutines.flow.Flow

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun users(
        username: String,
        page: Int,
        perPage: Int
    ): Flow<ApiResponse<List<Github>>> {
        return apiService.users(username, page, perPage).asFlowStateEvent {
            it.items.mapToGithub()
        }
    }
}