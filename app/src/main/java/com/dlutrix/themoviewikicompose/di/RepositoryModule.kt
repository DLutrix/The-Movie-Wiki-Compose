package com.dlutrix.themoviewikicompose.di

import com.dlutrix.themoviewikicompose.data.source.network.TMDBApiService
import com.dlutrix.themoviewikicompose.data.source.network.nowplayingmovies.NowPlayingMoviesRepository
import com.dlutrix.themoviewikicompose.data.source.network.upcomingmovies.UpcomingMoviesRepository
import com.dlutrix.themoviewikicompose.data.source.persistence.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideUpcomingMoviesRepository(
        api: TMDBApiService,
        db: MovieDatabase
    ) = UpcomingMoviesRepository(api, db)

    @Provides
    @ViewModelScoped
    fun provideNowPlayingMoviesRepository(
        api: TMDBApiService,
        db: MovieDatabase
    ) = NowPlayingMoviesRepository(api, db)
}