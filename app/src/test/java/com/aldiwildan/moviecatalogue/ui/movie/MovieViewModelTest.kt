package com.aldiwildan.moviecatalogue.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.aldiwildan.moviecatalogue.data.source.MovieRepository
import com.aldiwildan.moviecatalogue.data.source.local.entity.MovieEntity
import com.aldiwildan.moviecatalogue.utils.DataDummy
import com.aldiwildan.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel

    private val dummyMovie = Resource.success(DataDummy.generateDataDummyMovies())
    private val movieId = dummyMovie.data?.get(0)?.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var movieDetailObserver: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var moviesObserver: Observer<Resource<PagedList<MovieEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Before
    fun setup() {
        viewModel = MovieViewModel(movieRepository)
        viewModel.setSelectedMovie(movieId.toString())
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getMovies() {
        val dummyMovies = Resource.success(pagedList)
        `when`(dummyMovies.data?.size).thenReturn(10)

        val movies = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        movies.value = dummyMovies

        `when`(movieRepository.getAllMovies()).thenReturn(movies)
        val movieEntities = viewModel.getMovies().value?.data
        verify(movieRepository).getAllMovies()
        assertNotNull(movieEntities)
        assertEquals(10, movieEntities?.size)

        viewModel.getMovies().observeForever(moviesObserver)
        verify(moviesObserver).onChanged(dummyMovies)
    }

    @Test
    fun setSelectedMovie() {
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = Resource.success(DataDummy.generateDataDummyMovies()[0])

        `when`(movieRepository.getMovie(movieId)).thenReturn(movie)

        viewModel.getMovie.observeForever(movieDetailObserver)
        verify(movieDetailObserver).onChanged(movie.value)
    }

    @Test
    fun getMovie() {
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = Resource.success(DataDummy.generateDataDummyMovies()[0])

        `when`(movieRepository.getMovie(movieId)).thenReturn(movie)

        viewModel.getMovie.observeForever(movieDetailObserver)
        verify(movieDetailObserver).onChanged(movie.value)
    }
}