package com.dlutrix.themoviewikicompose.ui.detail

import androidx.lifecycle.ViewModel
import com.dlutrix.themoviewikicompose.data.model.MovieDetail
import com.dlutrix.themoviewikicompose.data.source.network.moviedetail.MovieDetailRepository
import com.dlutrix.themoviewikicompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) : ViewModel() {

    suspend fun getMovieDetail(movieId: Int): Resource<MovieDetail> {
        return movieDetailRepository.getMovieDetail(movieId)
    }
}