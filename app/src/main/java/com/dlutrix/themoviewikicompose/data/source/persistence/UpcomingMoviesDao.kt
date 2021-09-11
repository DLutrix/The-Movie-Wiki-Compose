package com.dlutrix.themoviewikicompose.data.source.persistence

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.dlutrix.themoviewikicompose.data.entity.Movie

@Dao
interface UpcomingMoviesDao {

    @Query("REPLACE INTO movies (backdropPath, id, overview, popularity, posterPath, title, voteAverage, isUpcomingMovies) VALUES (:backdropPath, :id, :overview, :popularity, :posterPath, :title, :voteAverage, :isUpcomingMovies)")
    suspend fun insertUpcomingMovies(
        backdropPath: String?,
        id: Int,
        overview: String?,
        popularity: Double?,
        posterPath: String?,
        title: String?,
        voteAverage: Double?,
        isUpcomingMovies: Boolean = true
    )

    @Query("SELECT * FROM movies WHERE isUpcomingMovies = 1 ORDER by popularity DESC")
    fun getAllMovies(): PagingSource<Int, Movie>

    @Query("DELETE FROM movies WHERE isUpcomingMovies = 1")
    suspend fun deleteAllMovies()
}