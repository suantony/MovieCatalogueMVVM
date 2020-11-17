package com.example.suantony.moviecatalogue.utils

import com.example.suantony.moviecatalogue.data.source.remote.response.MovieResponse
import com.example.suantony.moviecatalogue.data.source.remote.response.TvShowResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object RetrofitMovie {
    fun create(): MovieService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.themoviedb.org/3/")
            .build()
        return retrofit.create(MovieService::class.java)
    }
}

interface MovieService {
    @GET("movie/now_playing")
    fun getMovie(@Query("api_key") api_key: String): Call<MovieResponse>

    @GET("tv/popular")
    fun getTvShow(@Query("api_key") api_key: String): Call<TvShowResponse>
}