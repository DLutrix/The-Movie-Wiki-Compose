package com.dlutrix.themoviewikicompose.ui.discover

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.dlutrix.themoviewikicompose.data.source.network.nowplayingmovies.NowPlayingMoviesRepository
import com.dlutrix.themoviewikicompose.data.source.network.upcomingmovies.UpcomingMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    upcomingMoviesRepository: UpcomingMoviesRepository,
    private val nowPlayingMoviesRepository: NowPlayingMoviesRepository
) : ViewModel() {

    @ExperimentalPagingApi
    val upcomingMovies = upcomingMoviesRepository.getMoviesFromMediator().cachedIn(viewModelScope)

    private val _nowPlayingMovies = MutableLiveData(true)

    @ExperimentalCoroutinesApi
    val nowPlayingMovies = _nowPlayingMovies.switchMap {
        nowPlayingMoviesRepository.getNowPlayingMovies(it).asLiveData()
    }

    fun refreshNowPlayingMovies() {
        _nowPlayingMovies.value = true
    }
}