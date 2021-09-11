package com.dlutrix.themoviewikicompose.data.source.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dlutrix.themoviewikicompose.data.entity.Movie
import com.dlutrix.themoviewikicompose.data.entity.UpcomingMoviesRemoteKey

@Database(
    entities = [Movie::class, UpcomingMoviesRemoteKey::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun upcomingMoviesDao(): UpcomingMoviesDao
    abstract fun upcomingMoviesRemoteKeyDao(): UpcomingMoviesRemoteKeyDao
    abstract fun nowPlayingMoviesDao(): NowPlayingMoviesDao
}