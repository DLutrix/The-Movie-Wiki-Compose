package com.dlutrix.themoviewikicompose.di

import android.content.Context
import androidx.room.Room
import com.dlutrix.themoviewikicompose.data.source.persistence.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(
        @ApplicationContext appContext: Context
    ) = Room.databaseBuilder(appContext, MovieDatabase::class.java, "movie.db").build()
}