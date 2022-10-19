package com.astro.test.irwan.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.*
import com.astro.test.irwan.core.data.source.local.entity.GithubEntity
import com.astro.test.irwan.core.domain.model.Github

@Dao
interface GithubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(story: List<GithubEntity>)

    @Query("SELECT * FROM github WHERE name LIKE '%' || :username || '%' ORDER BY " +
            "CASE WHEN :isAsc = 1 THEN name END ASC, " +
            "CASE WHEN :isAsc = 0 THEN name END DESC")
    fun getUser(username: String, isAsc: Boolean): PagingSource<Int, Github>

    @Query("DELETE FROM github")
    suspend fun deleteUsers()

    @Update
    suspend fun updateFavorite(githubEntity: GithubEntity)

}