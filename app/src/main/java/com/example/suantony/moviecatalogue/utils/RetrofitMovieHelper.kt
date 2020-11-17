package com.example.suantony.moviecatalogue.utils

import android.util.Log
import com.example.suantony.moviecatalogue.BuildConfig
import com.example.suantony.moviecatalogue.data.source.remote.response.MovieResponse
import com.example.suantony.moviecatalogue.data.source.remote.response.MovieResponseItem
import com.example.suantony.moviecatalogue.data.source.remote.response.TvShowResponse
import com.example.suantony.moviecatalogue.data.source.remote.response.TvShowResponseItem
import retrofit2.Call
import retrofit2.Response

class RetrofitMovieHelper {
    private val API_KEY = BuildConfig.API_KEY
    private val movieService =
        RetrofitMovie.create()

    fun loadMovies(callback: LoadMovieCallback) {
        val movieList = ArrayList<MovieResponseItem>()

        movieService.getMovie(API_KEY)
            .enqueue(object : retrofit2.Callback<MovieResponse> {

                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()

                        if (data != null) {
                            data.results?.map {
                                if (it != null) {
                                    movieList.add(it)
                                }
                            }
                        }
                        callback.onAllMovieReceived(movieList)
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, error: Throwable) {
                    Log.e("tag", "error ${error.message}")
                }
            })
    }


    fun loadTvShow(callback: LoadTvShowCallback) {
        val tvShowList = ArrayList<TvShowResponseItem>()

        movieService.getTvShow(API_KEY)
            .enqueue(object : retrofit2.Callback<TvShowResponse> {
                override fun onResponse(
                    call: Call<TvShowResponse>,
                    response: Response<TvShowResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()

                        if (data != null) {
                            data.results?.map {
                                if (it != null) {
                                    tvShowList.add(it)
                                }
                            }
                        }
                        callback.onAllTvShowReceived(tvShowList)
                    }
                }

                override fun onFailure(call: Call<TvShowResponse>, error: Throwable) {
                    Log.e("tag", "error ${error.message}")
                }
            })
    }

}

interface LoadMovieCallback {
    fun onAllMovieReceived(value: List<MovieResponseItem>)
}

interface LoadTvShowCallback {
    fun onAllTvShowReceived(value: List<TvShowResponseItem>)
}


