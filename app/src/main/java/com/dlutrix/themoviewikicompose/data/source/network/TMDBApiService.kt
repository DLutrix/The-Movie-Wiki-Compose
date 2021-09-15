package com.dlutrix.themoviewikicompose.data.source.network

import com.dlutrix.themoviewikicompose.data.model.MovieDetail
import com.dlutrix.themoviewikicompose.data.model.NowPlayingMovies
import com.dlutrix.themoviewikicompose.data.model.UpcomingMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(
        @Path("movieId") movieId: Int,
        @Query("append_to_response") appendToResponse: String = "images"
    ): Response<MovieDetail>
}