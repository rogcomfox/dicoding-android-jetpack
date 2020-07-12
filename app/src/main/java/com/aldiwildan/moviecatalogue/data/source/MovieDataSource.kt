package com.aldiwildan.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.aldiwildan.moviecatalogue.data.source.local.entity.MovieEntity
import com.aldiwildan.moviecatalogue.data.source.local.entity.TvShowEntity
import com.aldiwildan.moviecatalogue.vo.Resource

interface MovieDataSource {

    fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>>

    fun getAllTvShows(): LiveData<Resource<PagedList<TvShowEntity>>>

    fun getMovie(movieId: String?): LiveData<Resource<MovieEntity>>

    fun getTvShow(tvShowId: String?): LiveData<Resource<TvShowEntity>>

    fun setMovieFavorite(movie: MovieEntity, isFavorite: Boolean)

    fun setTvShowFavorite(tvShow: TvShowEntity, isFavorite: Boolean)

    fun getAllMoviesFavorite(): LiveData<PagedList<MovieEntity>>

    fun getAllTvShowsFavorite(): LiveData<PagedList<TvShowEntity>>

}