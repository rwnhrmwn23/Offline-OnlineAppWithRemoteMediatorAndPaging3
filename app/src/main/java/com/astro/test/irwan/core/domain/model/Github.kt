package com.astro.test.irwan.core.domain.model

data class Github(
    val id: Int,
    val name: String,
    val image: String,
    val isFavorite: Boolean = false
)