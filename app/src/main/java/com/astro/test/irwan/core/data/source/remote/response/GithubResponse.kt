package com.astro.test.irwan.core.data.source.remote.response

data class GithubResponse(
    val incomplete_results: Boolean?,
    val items: List<Item>?,
    val total_count: Int?
) {
    data class Item(
        val avatar_url: String?,
        val id: Int?,
        val login: String?
    )
}