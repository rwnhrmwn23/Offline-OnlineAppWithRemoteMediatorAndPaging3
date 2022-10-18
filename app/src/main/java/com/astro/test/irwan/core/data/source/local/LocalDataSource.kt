package com.astro.test.irwan.core.data.source.local

import com.astro.test.irwan.core.data.source.local.entity.RemoteKeysEntity
import com.astro.test.irwan.core.data.source.local.entity.GithubEntity
import com.astro.test.irwan.core.data.source.local.room.RemoteKeysDao
import com.astro.test.irwan.core.data.source.local.room.GithubDao

class LocalDataSource(private val githubDao: GithubDao, private val remoteKeysDao: RemoteKeysDao) {

    fun getUser(username: String, isAsc: Boolean) = githubDao.getUser(username, isAsc)

    suspend fun insertUsers(github: List<GithubEntity>) = githubDao.insertUsers(github)

    suspend fun deleteUsers() = githubDao.deleteUsers()

    suspend fun insertAll(remoteKey: List<RemoteKeysEntity>) = remoteKeysDao.insertAll(remoteKey)

    suspend fun getRemoteKeysId(id: Int) = remoteKeysDao.getRemoteKeysId(id)

    suspend fun deleteRemoteKeys() = remoteKeysDao.deleteRemoteKeys()

}