package com.dlutrix.themoviewikicompose.data.source.persistence

import androidx.room.Dao
import androidx.room.Query
import com.dlutrix.themoviewikicompose.data.entity.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface NowPlayingMoviesDao {

    @Query("REPLACE INTO movies (backdropPath, id, overview, popularity, posterPath, title, voteAverage, isUpcomingMovies) VALUES (:backdropPath, :id, :overview, :popularity, :posterPath, :title, :voteAverage, :isUpcomingMovies)")
    suspend fun insertMovies(
        backdropPath: String?,
        id: Int,
        overview: String?,
        popularity: Double?,
        posterPath: String?,
        title: String?,
        voteAverage: Double?,
        isUpcomingMovies: Boolean = false
    )

    @Query("SELECT * FROM movies WHERE isUpcomingMovies = 0 ORDER by popularity DESC")
    fun getAllMovies(): Flow<List<Movie>>

    @Query("DELETE FROM movies WHERE isUpcomingMovies = 0")
    suspend fun deleteAllMovies()
}