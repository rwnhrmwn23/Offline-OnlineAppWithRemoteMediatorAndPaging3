package com.astro.test.irwan.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.astro.test.irwan.core.data.source.local.entity.RemoteKeysEntity
import com.astro.test.irwan.core.data.source.local.entity.GithubEntity

@Database(
    entities = [GithubEntity::class, RemoteKeysEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun githubDao(): GithubDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}