package com.aldiwildan.moviecatalogue.di

import android.content.Context
import com.aldiwildan.moviecatalogue.data.source.MovieRepository
import com.aldiwildan.moviecatalogue.data.source.local.LocalDataSource
import com.aldiwildan.moviecatalogue.data.source.local.room.MovieDatabase
import com.aldiwildan.moviecatalogue.data.source.remote.RemoteDataSource
import com.aldiwildan.moviecatalogue.network.ApiClient
import com.aldiwildan.moviecatalogue.utils.AppExecutors

object Injection {

    fun provideRepository(context: Context): MovieRepository {
        val database = MovieDatabase.getInstance(context)

        val apiClient = ApiClient.create()

        val remoteDataSource = RemoteDataSource.getInstance(apiClient)
        val localDataSource = LocalDataSource.getInstance(database.movieDao())
        val appExecutors = AppExecutors()

        return MovieRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}
