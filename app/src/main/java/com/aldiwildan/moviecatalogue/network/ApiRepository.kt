package com.aldiwildan.moviecatalogue.network

import com.aldiwildan.moviecatalogue.data.source.remote.response.MovieResponse
import com.aldiwildan.moviecatalogue.data.source.remote.response.MovieResultResponse
import com.aldiwildan.moviecatalogue.data.source.remote.response.TvShowResponse
import com.aldiwildan.moviecatalogue.data.source.remote.response.TvShowResultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRepository {

    @GET("movie/now_playing")
    fun getMovies(@Query("api_key") apiKey: String?): Call<MovieResultResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieId: String?,
        @Query("api_key") apiKey: String?
    ): Call<MovieResponse>

    @GET("tv/popular")
    fun getTvShows(@Query("api_key") apiKey: String?): Call<TvShowResultResponse>

    @GET("tv/{tv_id}")
    fun getTvShowDetail(
        @Path("tv_id") tvId: String?,
        @Query("api_key") apiKey: String?
    ): Call<TvShowResponse>
}