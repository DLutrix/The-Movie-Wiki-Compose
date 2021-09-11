package com.dlutrix.themoviewikicompose.data.source.network.upcomingmovies

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.dlutrix.themoviewikicompose.data.entity.Movie
import com.dlutrix.themoviewikicompose.data.entity.UpcomingMoviesRemoteKey
import com.dlutrix.themoviewikicompose.data.source.network.TMDBApiService
import com.dlutrix.themoviewikicompose.data.source.persistence.MovieDatabase
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class UpcomingMoviesRemoteMediator(
    private val api: TMDBApiService,
    private val db: MovieDatabase
) : RemoteMediator<Int, Movie>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        return try {
            val response = api.getUpcomingMovies(page)
            val isEndOfList = response.page == response.totalPages

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.upcomingMoviesDao().deleteAllMovies()
                    db.upcomingMoviesRemoteKeyDao().deleteAllUpcomingMoviesRemoteKey()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = response.results!!.map {
                    UpcomingMoviesRemoteKey(it.id!!, prevKey, nextKey)
                }

                response.results.map {
                    db.upcomingMoviesDao().insertUpcomingMovies(
                        backdropPath = it.backdropPath,
                        id = it.id!!,
                        overview = it.overview,
                        popularity = it.popularity,
                        posterPath = it.posterPath,
                        title = it.title,
                        voteAverage = it.voteAverage,
                    )
                }
                db.upcomingMoviesRemoteKeyDao().insertAllKeys(keys)
            }
            MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (_: IOException) {
            MediatorResult.Error(Throwable("Unexpected Error"))
        } catch (e: HttpException) {
            MediatorResult.Error(Throwable("No connection"))
        }
    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, Movie>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyClosesToCurrentPosition(state)
                remoteKey?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKey = getFirstRemoteKey(state)
                remoteKey?.prevKey
                    ?: MediatorResult.Success(endOfPaginationReached = remoteKey != null)
            }
            LoadType.APPEND -> {
                val remoteKey = getLastRemoteKey(state)
                remoteKey?.nextKey
                    ?: MediatorResult.Success(endOfPaginationReached = remoteKey != null)
            }
        }
    }

    private suspend fun getRemoteKeyClosesToCurrentPosition(state: PagingState<Int, Movie>): UpcomingMoviesRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                db.upcomingMoviesRemoteKeyDao().getUpcomingMovieRemoteKey(id)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, Movie>): UpcomingMoviesRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { data -> db.upcomingMoviesRemoteKeyDao().getUpcomingMovieRemoteKey(data.id) }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, Movie>): UpcomingMoviesRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { data -> db.upcomingMoviesRemoteKeyDao().getUpcomingMovieRemoteKey(data.id) }
    }
}