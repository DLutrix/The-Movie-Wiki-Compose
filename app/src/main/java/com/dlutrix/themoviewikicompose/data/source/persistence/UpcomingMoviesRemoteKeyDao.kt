package com.dlutrix.themoviewikicompose.data.source.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dlutrix.themoviewikicompose.data.entity.UpcomingMoviesRemoteKey

@Dao
interface UpcomingMoviesRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllKeys(remoteKey: List<UpcomingMoviesRemoteKey>)

    @Query("SELECT * FROM upcoming_movies_remote_keys WHERE id= :id")
    suspend fun getUpcomingMovieRemoteKey(id: Int): UpcomingMoviesRemoteKey?

    @Query("DELETE FROM upcoming_movies_remote_keys")
    suspend fun deleteAllUpcomingMoviesRemoteKey()
}