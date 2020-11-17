package com.example.suantony.moviecatalogue.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.suantony.moviecatalogue.data.source.local.LocalDataSource
import com.example.suantony.moviecatalogue.data.source.local.entity.MovieEntity
import com.example.suantony.moviecatalogue.data.source.local.entity.TvShowEntity
import com.example.suantony.moviecatalogue.data.source.remote.ApiResponse
import com.example.suantony.moviecatalogue.data.source.remote.RemoteDataSource
import com.example.suantony.moviecatalogue.data.source.remote.response.MovieResponseItem
import com.example.suantony.moviecatalogue.data.source.remote.response.TvShowResponseItem
import com.example.suantony.moviecatalogue.utils.AppExecutors
import com.example.suantony.moviecatalogue.utils.vo.Resource

class MovieRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    MovieDataResource {
    companion object {
        @Volatile
        private var instance: MovieRepository? = null
        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteData, localData, appExecutors)
            }
    }

    override fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponseItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<MovieResponseItem>>> {
                return remoteDataSource.getAllMovies()
            }

            override fun saveCallResult(data: List<MovieResponseItem>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in data) {
                    val movie =
                        MovieEntity(
                            response.id,
                            response.title,
                            response.releaseDate,
                            response.popularity,
                            response.overview,
                            false,
                            response.posterPath
                        )
                    movieList.add(movie)
                }
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }


    override fun getAllTvShow(): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object :
            NetworkBoundResource<PagedList<TvShowEntity>, List<TvShowResponseItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllTvShow(), config).build()
            }


            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvShowResponseItem>>> =
                remoteDataSource.getAllTvShow()

            override fun saveCallResult(data: List<TvShowResponseItem>) {
                val tvShowList = ArrayList<TvShowEntity>()
                for (response in data) {
                    val movie =
                        TvShowEntity(
                            response.id,
                            response.originalName,
                            response.firstAirDate,
                            response.popularity,
                            response.overview,
                            false,
                            response.posterPath
                        )
                    tvShowList.add(movie)
                }
                localDataSource.insertTvShows(tvShowList)
            }
        }.asLiveData()
    }

    override fun getMovieDetail(movie_id: Int?): LiveData<MovieEntity> =
        localDataSource.getMovieWithId(movie_id.toString())


    override fun getTvShowDetail(tv_show_id: Int?): LiveData<TvShowEntity> =
        localDataSource.getTvShowWithId(tv_show_id.toString())


    override fun getBookmarkedMovies(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getBookmarkedMovie(), config).build()
    }

    override fun getBookmarkedTvShow(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getBookmarkedTvShow(), config).build()
    }

    override fun setMovieBookmark(movie: MovieEntity, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setMovieBookmark(movie, state) }
    }

    override fun setTvShowBookmark(tvShow: TvShowEntity, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setTvShowBookmark(tvShow, state) }
    }
}