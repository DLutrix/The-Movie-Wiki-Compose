package com.dlutrix.themoviewikicompose.data.source.network.upcomingmovies

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dlutrix.themoviewikicompose.data.entity.Movie
import com.dlutrix.themoviewikicompose.data.source.network.TMDBApiService
import com.dlutrix.themoviewikicompose.data.source.persistence.MovieDatabase
import kotlinx.coroutines.flow.Flow

class UpcomingMoviesRepository(
    private val api: TMDBApiService,
    private val db: MovieDatabase,
) {

    @ExperimentalPagingApi
    fun getMoviesFromMediator(): Flow<PagingData<Movie>> {
        val pagingSourceFactory = { db.upcomingMoviesDao().getAllMovies() }

        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            remoteMediator = UpcomingMoviesRemoteMediator(
                api,
                db
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}