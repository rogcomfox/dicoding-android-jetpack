package com.aldiwildan.moviecatalogue.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import com.aldiwildan.moviecatalogue.data.source.local.LocalDataSource
import com.aldiwildan.moviecatalogue.data.source.local.entity.MovieEntity
import com.aldiwildan.moviecatalogue.data.source.local.entity.TvShowEntity
import com.aldiwildan.moviecatalogue.data.source.remote.RemoteDataSource
import com.aldiwildan.moviecatalogue.utils.AppExecutors
import com.aldiwildan.moviecatalogue.utils.DataDummy
import com.aldiwildan.moviecatalogue.utils.PagedListUtil
import com.aldiwildan.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@Suppress("UNCHECKED_CAST")
class MovieRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutor = mock(AppExecutors::class.java)
    private val movieRepository = FakeMovieRepository(remote, local, appExecutor)

    private val movieResponse = DataDummy.generateDataDummyMovies()
    private val tvShowResponse = DataDummy.generateDataDummyTvShow()

    private val movieId = movieResponse[0].id
    private val tvShowId = tvShowResponse[0].id

    @Test
    fun getAllMovies() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovies()).thenReturn(dataSourceFactory)
        movieRepository.getAllMovies()

        val movieEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDataDummyMovies()))
        verify(local).getAllMovies()
        assertNotNull(movieEntities)
        assertEquals(movieResponse.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getAllTvShows() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getAllTvShows()).thenReturn(dataSourceFactory)
        movieRepository.getAllTvShows()

        val tvShowEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDataDummyTvShow()))
        verify(local).getAllTvShows()
        assertNotNull(tvShowEntities)
        assertEquals(tvShowResponse.size.toLong(), tvShowEntities.data?.size?.toLong())
    }

    @Test
    fun getMovie() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovies()).thenReturn(dataSourceFactory)
        movieRepository.getMovie(movieId)

        val movieEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDataDummyMovies()))
        verify(local).getMovie(movieId)
        assertNotNull(movieEntities)
        assertEquals(movieResponse[0].id, movieEntities.data?.get(0)?.id)
    }

    @Test
    fun getTvShow() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getAllTvShows()).thenReturn(dataSourceFactory)
        movieRepository.getTvShow(tvShowId)

        val tvShowEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDataDummyTvShow()))
        verify(local).getTvShow(tvShowId)
        assertNotNull(tvShowEntities)
        assertEquals(tvShowResponse[0].id, tvShowEntities.data?.get(0)?.id)
    }
}