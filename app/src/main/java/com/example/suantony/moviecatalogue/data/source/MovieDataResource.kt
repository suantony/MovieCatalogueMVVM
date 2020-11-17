package com.example.suantony.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.suantony.moviecatalogue.data.source.local.entity.MovieEntity
import com.example.suantony.moviecatalogue.data.source.local.entity.TvShowEntity
import com.example.suantony.moviecatalogue.utils.vo.Resource

interface MovieDataResource {
    fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>>

    fun getAllTvShow(): LiveData<Resource<PagedList<TvShowEntity>>>

    fun getMovieDetail(movie_id: Int?): LiveData<MovieEntity>

    fun getTvShowDetail(tv_show_id: Int?): LiveData<TvShowEntity>

    fun getBookmarkedMovies(): LiveData<PagedList<MovieEntity>>

    fun getBookmarkedTvShow(): LiveData<PagedList<TvShowEntity>>

    fun setMovieBookmark(movie: MovieEntity, state: Boolean)

    fun setTvShowBookmark(tvShow: TvShowEntity, state: Boolean)

}