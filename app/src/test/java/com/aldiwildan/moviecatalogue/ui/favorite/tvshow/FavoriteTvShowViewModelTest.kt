package com.aldiwildan.moviecatalogue.ui.favorite.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.aldiwildan.moviecatalogue.data.source.MovieRepository
import com.aldiwildan.moviecatalogue.data.source.local.entity.TvShowEntity
import com.nhaarman.mockitokotlin2.verify
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteTvShowViewModelTest {

    private lateinit var viewModel: FavoriteTvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var tvShowObserver: Observer<PagedList<TvShowEntity>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowEntity>

    @Before
    fun setup() {
        viewModel = FavoriteTvShowViewModel(movieRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getTvShows() {
        val dummyTvShows = pagedList
        `when`(dummyTvShows.size).thenReturn(10)
        val movies = MutableLiveData<PagedList<TvShowEntity>>()
        movies.value = dummyTvShows

        `when`(movieRepository.getAllTvShowsFavorite()).thenReturn(movies)
        val tvShowEntities = viewModel.getTvShows().value
        verify(movieRepository).getAllTvShowsFavorite()
        Assert.assertNotNull(tvShowEntities)
        Assert.assertEquals(10, tvShowEntities?.size)

        viewModel.getTvShows().observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dummyTvShows)
    }
}