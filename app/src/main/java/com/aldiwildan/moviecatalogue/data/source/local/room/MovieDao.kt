package com.aldiwildan.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.aldiwildan.moviecatalogue.data.source.local.entity.MovieEntity
import com.aldiwildan.moviecatalogue.data.source.local.entity.TvShowEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movieEntities")
    fun getAllMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movieEntities WHERE ID = :id")
    fun getMovie(id: String): LiveData<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movies: List<MovieEntity>)

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun updateMovie(movie: MovieEntity)

    @Query("SELECT * FROM movieEntities WHERE favorite = 1")
    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM tvShowEntities")
    fun getAllTvShows(): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM tvShowEntities WHERE ID = :id")
    fun getTvShow(id: String): LiveData<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(tvShows: List<TvShowEntity>)

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun updateTvShow(tvShow: TvShowEntity)

    @Query("SELECT * FROM tvShowEntities WHERE favorite = 1")
    fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowEntity>

}