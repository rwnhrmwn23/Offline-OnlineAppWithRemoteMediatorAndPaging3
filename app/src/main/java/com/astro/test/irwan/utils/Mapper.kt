package com.astro.test.irwan.utils

import com.astro.test.irwan.core.data.source.local.entity.GithubEntity
import com.astro.test.irwan.core.data.source.remote.response.GithubResponse
import com.astro.test.irwan.core.domain.model.Github

object Mapper {

    fun List<GithubResponse.Item>?.mapToGithub(): List<Github> {
        val listUsers = ArrayList<Github>()
        this?.map {
            listUsers.add(Github(it.id ?: 0, it.login.orEmpty(), it.avatar_url.orEmpty()))
        }
        return listUsers
    }

    fun List<Github>.mapToGithubEntities(): List<GithubEntity> {
        val githubEntities = ArrayList<GithubEntity>()
        for (it in this) {
            val story = GithubEntity(
                id = it.id,
                name = it.name,
                image = it.image,
                isFavorite = false
            )
            githubEntities.add(story)
        }
        return githubEntities
    }

    fun Github.mapToGithubEntity() = GithubEntity(
        id = id,
        name = name,
        image = image,
        isFavorite = isFavorite
    )
}