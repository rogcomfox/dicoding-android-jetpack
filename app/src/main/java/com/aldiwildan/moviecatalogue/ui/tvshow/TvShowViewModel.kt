package com.aldiwildan.moviecatalogue.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.aldiwildan.moviecatalogue.data.source.MovieRepository
import com.aldiwildan.moviecatalogue.data.source.local.entity.TvShowEntity
import com.aldiwildan.moviecatalogue.vo.Resource

class TvShowViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private var tvShowId = MutableLiveData<String>()

    fun getTvShows(): LiveData<Resource<PagedList<TvShowEntity>>> = movieRepository.getAllTvShows()

    fun setSelectedTvShow(id: String) {
        this.tvShowId.value = id
    }

    val getTvShow: LiveData<Resource<TvShowEntity>> =
        Transformations.switchMap(tvShowId) { tvShowId ->
            movieRepository.getTvShow(tvShowId)
        }

    fun setTvShowFavorite() {
        val tvShowResource = getTvShow.value
        if (tvShowResource != null) {
            val tvShow = tvShowResource.data
            if (tvShow != null) {
                val newState = !tvShow.favorite
                movieRepository.setTvShowFavorite(tvShow, newState)
            }
        }
    }
}
