package com.example.suantony.moviecatalogue.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.suantony.moviecatalogue.data.source.remote.response.MovieResponseItem
import com.example.suantony.moviecatalogue.data.source.remote.response.TvShowResponseItem
import com.example.suantony.moviecatalogue.utils.EspressoIdlingResource
import com.example.suantony.moviecatalogue.utils.LoadMovieCallback
import com.example.suantony.moviecatalogue.utils.LoadTvShowCallback
import com.example.suantony.moviecatalogue.utils.RetrofitMovieHelper

class RemoteDataSource private constructor(private val retrofitMovieHelper: RetrofitMovieHelper) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: RetrofitMovieHelper): RemoteDataSource =
            instance
                ?: synchronized(this) {
                    instance
                        ?: RemoteDataSource(
                            helper
                        )
                }
    }

    fun getAllMovies(): LiveData<ApiResponse<List<MovieResponseItem>>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<List<MovieResponseItem>>>()
        retrofitMovieHelper.loadMovies(object :
            LoadMovieCallback {
            override fun onAllMovieReceived(value: List<MovieResponseItem>) {
                resultMovie.value = ApiResponse.success(value)
                EspressoIdlingResource.decrement()
            }
        })
        return resultMovie
    }

    fun getAllTvShow(): LiveData<ApiResponse<List<TvShowResponseItem>>> {
        EspressoIdlingResource.increment()
        val resultTvShow = MutableLiveData<ApiResponse<List<TvShowResponseItem>>>()
        retrofitMovieHelper.loadTvShow(object : LoadTvShowCallback {
            override fun onAllTvShowReceived(value: List<TvShowResponseItem>) {
                resultTvShow.value = ApiResponse.success(value)
                EspressoIdlingResource.decrement()
            }
        })
        return resultTvShow
    }

}

//interface RemoteDataMovieCallback {
//    fun onSuccessReceivedMovieData(value: List<MovieResponseItem>)
//}
//
//interface RemoteDataTvShowCallback {
//    fun onSuccessReceivedTvShowData(value: List<TvShowResponseItem>)
//}
