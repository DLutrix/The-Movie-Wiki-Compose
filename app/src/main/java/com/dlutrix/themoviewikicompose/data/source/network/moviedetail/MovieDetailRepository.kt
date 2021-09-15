package com.dlutrix.themoviewikicompose.data.source.network.moviedetail

import com.dlutrix.themoviewikicompose.data.model.MovieDetail
import com.dlutrix.themoviewikicompose.data.source.network.TMDBApiService
import com.dlutrix.themoviewikicompose.util.Resource
import retrofit2.HttpException
import java.io.IOException

class MovieDetailRepository(
    private val api: TMDBApiService
) {

    suspend fun getMovieDetail(movieId: Int): Resource<MovieDetail> {
        return try {
            val response = api.getMovieDetail(movieId)
            if (response.isSuccessful) {
                Resource.Success(response.body())
            } else {
                Resource.Error(null, Throwable("Failed to fetch data from server"))
            }
        } catch (e: IOException) {
            Resource.Error(null, Throwable("Unexpected error"))
        } catch (e: HttpException) {
            Resource.Error(null, Throwable("No connection"))
        }
    }
}