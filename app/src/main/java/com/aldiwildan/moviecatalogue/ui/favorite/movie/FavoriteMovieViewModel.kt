package com.aldiwildan.moviecatalogue.ui.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.aldiwildan.moviecatalogue.data.source.MovieRepository
import com.aldiwildan.moviecatalogue.data.source.local.entity.MovieEntity

class FavoriteMovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getMovies(): LiveData<PagedList<MovieEntity>> = movieRepository.getAllMoviesFavorite()
}