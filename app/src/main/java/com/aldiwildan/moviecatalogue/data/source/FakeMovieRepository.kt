package com.aldiwildan.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.aldiwildan.moviecatalogue.data.source.local.LocalDataSource
import com.aldiwildan.moviecatalogue.data.source.local.entity.MovieEntity
import com.aldiwildan.moviecatalogue.data.source.local.entity.TvShowEntity
import com.aldiwildan.moviecatalogue.data.source.remote.RemoteDataSource
import com.aldiwildan.moviecatalogue.data.source.remote.response.ApiResponse
import com.aldiwildan.moviecatalogue.data.source.remote.response.MovieResponse
import com.aldiwildan.moviecatalogue.data.source.remote.response.TvShowResponse
import com.aldiwildan.moviecatalogue.utils.AppExecutors
import com.aldiwildan.moviecatalogue.vo.Resource

class FakeMovieRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    MovieDataSource {

    private var config: PagedList.Config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(4)
        .setPageSize(4)
        .build()

    companion object {
        @Volatile
        private var instance: FakeMovieRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): FakeMovieRepository =
            instance ?: synchronized(this) {
                instance ?: FakeMovieRepository(remoteData, localData, appExecutors)
            }
    }

    override fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovies()

            override fun saveCallResult(data: List<MovieResponse>) {
                val movieResult = ArrayList<MovieEntity>()
                for (datas in data) {
                    val movie = MovieEntity(
                        datas.id.toString(),
                        datas.title,
                        datas.overview,
                        datas.rating,
                        datas.release,
                        datas.poster
                    )
                    movieResult.add(movie)
                }
                localDataSource.insertMovie(movieResult)
            }
        }.asLiveData()
    }

    override fun getAllTvShows(): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object :
            NetworkBoundResource<PagedList<TvShowEntity>, List<TvShowResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                return LivePagedListBuilder(localDataSource.getAllTvShows(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvShowResponse>>> =
                remoteDataSource.getAllTvShows()

            override fun saveCallResult(data: List<TvShowResponse>) {
                val tvShowResult = ArrayList<TvShowEntity>()
                for (datas in data) {
                    val tvShow = TvShowEntity(
                        datas.id.toString(),
                        datas.title,
                        datas.overview,
                        datas.rating,
                        datas.release,
                        datas.poster
                    )
                    tvShowResult.add(tvShow)
                }
                localDataSource.insertTvShow(tvShowResult)
            }

        }.asLiveData()
    }

    override fun getMovie(movieId: String?): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> =
                localDataSource.getMovie(movieId.toString())

            override fun shouldFetch(data: MovieEntity?): Boolean = data == null

            override fun createCall(): LiveData<ApiResponse<MovieResponse>> =
                remoteDataSource.getMovieDetail(movieId)

            override fun saveCallResult(data: MovieResponse) {
                val movie = MovieEntity(
                    data.id.toString(),
                    data.title,
                    data.overview,
                    data.rating,
                    data.release,
                    data.poster
                )
                localDataSource.updateMovie(movie)
            }


        }.asLiveData()
    }

    override fun getTvShow(tvShowId: String?): LiveData<Resource<TvShowEntity>> {
        return object : NetworkBoundResource<TvShowEntity, TvShowResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<TvShowEntity> =
                localDataSource.getTvShow(tvShowId.toString())

            override fun shouldFetch(data: TvShowEntity?): Boolean = data == null

            override fun createCall(): LiveData<ApiResponse<TvShowResponse>> =
                remoteDataSource.getTvShowDetail(tvShowId)

            override fun saveCallResult(data: TvShowResponse) {
                val tvShow = TvShowEntity(
                    data.id.toString(),
                    data.title,
                    data.overview,
                    data.rating,
                    data.release,
                    data.poster
                )
                localDataSource.updateTvShow(tvShow)
            }
        }.asLiveData()
    }

    override fun setMovieFavorite(movie: MovieEntity, isFavorite: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setMovieFavorite(movie, isFavorite)
        }
    }

    override fun setTvShowFavorite(tvShow: TvShowEntity, isFavorite: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setTvShowFavorite(tvShow, isFavorite)
        }
    }

    override fun getAllMoviesFavorite(): LiveData<PagedList<MovieEntity>> =
        LivePagedListBuilder(localDataSource.getFavoriteMovies(), config).build()

    override fun getAllTvShowsFavorite(): LiveData<PagedList<TvShowEntity>> =
        LivePagedListBuilder(localDataSource.getFavoriteTvShows(), config).build()

}