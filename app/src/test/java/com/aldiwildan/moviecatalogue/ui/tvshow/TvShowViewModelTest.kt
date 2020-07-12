package com.aldiwildan.moviecatalogue.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.aldiwildan.moviecatalogue.data.source.MovieRepository
import com.aldiwildan.moviecatalogue.data.source.local.entity.TvShowEntity
import com.aldiwildan.moviecatalogue.utils.DataDummy
import com.aldiwildan.moviecatalogue.vo.Resource
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {

    private lateinit var viewModel: TvShowViewModel

    private val dummyTvShow = Resource.success(DataDummy.generateDataDummyTvShow())
    private val tvShowId = dummyTvShow.data?.get(0)?.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var tvShowDetailObserver: Observer<Resource<TvShowEntity>>

    @Mock
    private lateinit var tvShowObserver: Observer<Resource<PagedList<TvShowEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowEntity>

    @Before
    fun setup() {
        viewModel = TvShowViewModel(movieRepository)
        viewModel.setSelectedTvShow(tvShowId.toString())
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getTvShows() {
        val dummyTvShows = Resource.success(pagedList)
        `when`(dummyTvShows.data?.size).thenReturn(10)

        val tvShows = MutableLiveData<Resource<PagedList<TvShowEntity>>>()
        tvShows.value = dummyTvShows

        `when`(movieRepository.getAllTvShows()).thenReturn(tvShows)
        val tvShowEntities = viewModel.getTvShows().value?.data
        verify(movieRepository).getAllTvShows()
        assertNotNull(tvShowEntities)
        assertEquals(10, tvShowEntities?.size)

        viewModel.getTvShows().observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dummyTvShows)
    }

    @Test
    fun setSelectedTvShow() {
        val tvShow = MutableLiveData<Resource<TvShowEntity>>()
        tvShow.value = Resource.success(DataDummy.generateDataDummyTvShow()[0])

        `when`(movieRepository.getTvShow(tvShowId)).thenReturn(tvShow)

        viewModel.getTvShow.observeForever(tvShowDetailObserver)
        verify(tvShowDetailObserver).onChanged(tvShow.value)
    }

    @Test
    fun getTvShow() {
        val tvShow = MutableLiveData<Resource<TvShowEntity>>()
        tvShow.value = Resource.success(DataDummy.generateDataDummyTvShow()[0])

        `when`(movieRepository.getTvShow(tvShowId)).thenReturn(tvShow)

        viewModel.getTvShow.observeForever(tvShowDetailObserver)
        verify(tvShowDetailObserver).onChanged(tvShow.value)
    }
}