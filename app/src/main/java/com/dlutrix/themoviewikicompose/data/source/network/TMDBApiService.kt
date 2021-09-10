package com.dlutrix.themoviewikicompose.data.source.network

import com.dlutrix.themoviewikicompose.data.model.NowPlayingMovies
import com.dlutrix.themoviewikicompose.data.model.UpcomingMovies
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApiService {

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int
    ): UpcomingMovies

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int = 1
    ): NowPlayingMovies
}