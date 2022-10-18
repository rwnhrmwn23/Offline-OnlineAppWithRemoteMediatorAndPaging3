package com.astro.test.irwan.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.astro.test.irwan.core.data.source.local.entity.GithubEntity

@Dao
interface GithubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(story: List<GithubEntity>)

    @Query("SELECT * FROM github WHERE name = :username")
    fun getUser(username: String): PagingSource<Int, GithubEntity>

    @Query("DELETE FROM github")
    suspend fun deleteUsers()

}