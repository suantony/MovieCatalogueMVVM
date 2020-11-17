package com.example.suantony.moviecatalogue.di

import android.content.Context
import com.example.suantony.moviecatalogue.data.source.MovieRepository
import com.example.suantony.moviecatalogue.data.source.local.LocalDataSource
import com.example.suantony.moviecatalogue.data.source.local.room.MovieDatabase
import com.example.suantony.moviecatalogue.data.source.remote.RemoteDataSource
import com.example.suantony.moviecatalogue.utils.AppExecutors
import com.example.suantony.moviecatalogue.utils.RetrofitMovieHelper

object Injection {
    fun provideRepository(context:Context): MovieRepository {
        val database = MovieDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(RetrofitMovieHelper())
        val localDataSource = LocalDataSource.getInstance(database.movieDao())
        val appExecutors = AppExecutors()

        return MovieRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}