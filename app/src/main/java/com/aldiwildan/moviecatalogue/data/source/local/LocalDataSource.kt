package com.aldiwildan.moviecatalogue.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.aldiwildan.moviecatalogue.data.source.local.entity.MovieEntity
import com.aldiwildan.moviecatalogue.data.source.local.entity.TvShowEntity
import com.aldiwildan.moviecatalogue.data.source.local.room.MovieDao

class LocalDataSource private constructor(private val mMovieDao: MovieDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieDao)
    }

    fun getAllMovies(): DataSource.Factory<Int, MovieEntity> = mMovieDao.getAllMovies()

    fun getMovie(id: String): LiveData<MovieEntity> = mMovieDao.getMovie(id)

    fun insertMovie(movies: List<MovieEntity>) = mMovieDao.insertMovie(movies)

    fun updateMovie(movie: MovieEntity) = mMovieDao.updateMovie(movie)

    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity> = mMovieDao.getFavoriteMovies()

    fun setMovieFavorite(movie: MovieEntity, isFavorite: Boolean) {
        movie.favorite = isFavorite
        mMovieDao.updateMovie(movie)
    }

    fun getAllTvShows(): DataSource.Factory<Int, TvShowEntity> = mMovieDao.getAllTvShows()

    fun getTvShow(id: String) = mMovieDao.getTvShow(id)

    fun insertTvShow(tvShows: List<TvShowEntity>) = mMovieDao.insertTvShow(tvShows)

    fun updateTvShow(tvShow: TvShowEntity) = mMovieDao.updateTvShow(tvShow)

    fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowEntity> = mMovieDao.getFavoriteTvShows()

    fun setTvShowFavorite(tvShow: TvShowEntity, isFavorite: Boolean) {
        tvShow.favorite = isFavorite
        mMovieDao.updateTvShow(tvShow)
    }

}