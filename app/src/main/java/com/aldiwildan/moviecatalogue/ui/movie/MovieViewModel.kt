package com.aldiwildan.moviecatalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.aldiwildan.moviecatalogue.data.source.MovieRepository
import com.aldiwildan.moviecatalogue.data.source.local.entity.MovieEntity
import com.aldiwildan.moviecatalogue.vo.Resource

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private var movieId = MutableLiveData<String>()

    fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> = movieRepository.getAllMovies()

    fun setSelectedMovie(id: String) {
        this.movieId.value = id
    }

    val getMovie: LiveData<Resource<MovieEntity>> =
        Transformations.switchMap(movieId) { movieId ->
            movieRepository.getMovie(movieId)
        }

    fun setMovieFavorite() {
        val movieResource = getMovie.value
        if (movieResource != null) {
            val movie = movieResource.data
            if (movie != null) {
                val newState = !movie.favorite
                movieRepository.setMovieFavorite(movie, newState)
            }
        }
    }
}
