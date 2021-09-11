package com.dlutrix.themoviewikicompose.data.source.network.nowplayingmovies

import androidx.room.withTransaction
import com.dlutrix.themoviewikicompose.data.source.network.TMDBApiService
import com.dlutrix.themoviewikicompose.data.source.persistence.MovieDatabase
import com.dlutrix.themoviewikicompose.util.networkBoundResource
import kotlinx.coroutines.delay

class NowPlayingMoviesRepository(
    private val api: TMDBApiService,
    private val db: MovieDatabase
) {

    fun getNowPlayingMovies(shouldFetch: Boolean) = networkBoundResource(
        query = {
            db.nowPlayingMoviesDao().getAllMovies()
        },
        fetch = {
            api.getNowPlayingMovies()
        },
        saveFetchResult = { data ->
            db.withTransaction {
                db.nowPlayingMoviesDao().deleteAllMovies()
                data.movies?.subList(0, 5)?.map {
                    db.nowPlayingMoviesDao().insertMovies(
                        backdropPath = it.backdropPath,
                        id = it.id!!,
                        overview = it.overview,
                        popularity = it.popularity,
                        posterPath = it.posterPath,
                        title = it.title,
                        voteAverage = it.voteAverage
                    )
                }
            }
        },
        shouldFetch = { shouldFetch }
    )
}