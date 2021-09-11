package com.dlutrix.themoviewikicompose.data.source.persistence

import androidx.room.Database
import com.dlutrix.themoviewikicompose.data.entity.Movie

@Database(
    entities = [Movie::class, UpcomingMoviesRemoteKeyDao::class],
    version = 1
)
abstract class MovieDatabase {
    abstract fun upcomingMoviesDao(): UpcomingMoviesDao
    abstract fun upcomingMoviesRemoteKeyDao(): UpcomingMoviesRemoteKeyDao
    abstract fun nowPlayingMoviesDao(): NowPlayingMoviesDao
}