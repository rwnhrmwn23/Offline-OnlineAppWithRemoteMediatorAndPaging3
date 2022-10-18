package com.astro.test.irwan.core.data.source.remote.network

import com.astro.test.irwan.core.data.source.remote.response.GithubResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun users(
        @Query("q") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Response<GithubResponse>

}