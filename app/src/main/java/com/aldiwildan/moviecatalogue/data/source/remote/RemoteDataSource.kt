package com.aldiwildan.moviecatalogue.data.source.remote

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aldiwildan.moviecatalogue.data.source.remote.response.*
import com.aldiwildan.moviecatalogue.network.ApiRepository
import com.aldiwildan.moviecatalogue.utils.Constants
import com.aldiwildan.moviecatalogue.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiRepository: ApiRepository) {

    private val handler = Handler()

    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS = 2000L

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiClient: ApiRepository): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(apiClient)
            }
    }

    fun getAllMovies(): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        handler.postDelayed({
            apiRepository.getMovies(Constants.API_KEY).enqueue(object :
                Callback<MovieResultResponse> {
                override fun onFailure(call: Call<MovieResultResponse>, t: Throwable) {
                    Log.d("getAllMovies", t.toString())
                    EspressoIdlingResource.decrement()
                }

                override fun onResponse(
                    call: Call<MovieResultResponse>,
                    response: Response<MovieResultResponse>
                ) {
                    resultMovie.value = ApiResponse.success(response.body()?.result!!)
                    EspressoIdlingResource.decrement()
                }

            })
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultMovie
    }

    fun getAllTvShows(): LiveData<ApiResponse<List<TvShowResponse>>> {
        EspressoIdlingResource.increment()
        val resultTvShow = MutableLiveData<ApiResponse<List<TvShowResponse>>>()
        handler.postDelayed({
            apiRepository.getTvShows(Constants.API_KEY).enqueue(object :
                Callback<TvShowResultResponse> {
                override fun onFailure(call: Call<TvShowResultResponse>, t: Throwable) {
                    Log.d("getAllTvShows", t.toString())
                    EspressoIdlingResource.decrement()
                }

                override fun onResponse(
                    call: Call<TvShowResultResponse>,
                    response: Response<TvShowResultResponse>
                ) {
                    resultTvShow.value = ApiResponse.success(response.body()?.result!!)
                    EspressoIdlingResource.decrement()
                }

            })
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultTvShow
    }

    fun getMovieDetail(movieId: String?): LiveData<ApiResponse<MovieResponse>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<MovieResponse>>()
        handler.postDelayed({
            apiRepository.getMovieDetail(movieId, Constants.API_KEY).enqueue(object :
                Callback<MovieResponse> {
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.d("getMovieDetail", t.toString())
                    EspressoIdlingResource.decrement()
                }

                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    resultMovie.value = ApiResponse.success(response.body()!!)
                    EspressoIdlingResource.decrement()
                }

            })
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultMovie
    }

    fun getTvShowDetail(tvShowId: String?): LiveData<ApiResponse<TvShowResponse>> {
        EspressoIdlingResource.increment()
        val resultTvShow = MutableLiveData<ApiResponse<TvShowResponse>>()
        handler.postDelayed({
            apiRepository.getTvShowDetail(tvShowId, Constants.API_KEY).enqueue(object :
                Callback<TvShowResponse> {
                override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                    Log.d("getTvShowDetail", t.toString())
                    EspressoIdlingResource.decrement()
                }

                override fun onResponse(
                    call: Call<TvShowResponse>,
                    response: Response<TvShowResponse>
                ) {
                    resultTvShow.value = ApiResponse.success(response.body()!!)
                    EspressoIdlingResource.decrement()
                }

            })
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultTvShow
    }
}