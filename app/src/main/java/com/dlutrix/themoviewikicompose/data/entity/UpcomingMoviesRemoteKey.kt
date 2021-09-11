package com.dlutrix.themoviewikicompose.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "upcoming_movies_remote_keys")
data class UpcomingMoviesRemoteKey(
    @PrimaryKey val id: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
