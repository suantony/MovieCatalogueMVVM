package com.example.suantony.moviecatalogue.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.example.suantony.moviecatalogue.data.source.local.entity.MovieEntity
import com.example.suantony.moviecatalogue.data.source.local.entity.TvShowEntity
import com.example.suantony.moviecatalogue.data.source.local.room.MovieDao

class LocalDataSource private constructor(private val mMovieDao: MovieDao) {
    companion object {
        private var INSTANCE: LocalDataSource? = null
        fun getInstance(movieDao: MovieDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieDao)
    }

    fun getAllMovies(): DataSource.Factory<Int, MovieEntity> = mMovieDao.getMovies()

    fun getAllTvShow(): DataSource.Factory<Int, TvShowEntity> = mMovieDao.getTvShow()

    fun getBookmarkedMovie(): DataSource.Factory<Int, MovieEntity> = mMovieDao.getBookmarkedMovie()

    fun getBookmarkedTvShow(): DataSource.Factory<Int, TvShowEntity> = mMovieDao.getBookmarkedTvShow()

    fun getMovieWithId(movieId: String): LiveData<MovieEntity> = mMovieDao.getMovieById(movieId)

    fun getTvShowWithId(tvShowId: String): LiveData<TvShowEntity> =
        mMovieDao.getTvShowById(tvShowId)

    fun insertMovies(movie: List<MovieEntity>) = mMovieDao.insertNovies(movie)

    fun insertTvShows(tvshow: List<TvShowEntity>) = mMovieDao.insertTvShow(tvshow)

    fun setMovieBookmark(movie: MovieEntity, newState: Boolean) {
        movie.bookmarked = newState
        mMovieDao.updateMovie(movie)
    }
    fun setTvShowBookmark(tvShow: TvShowEntity, newState: Boolean) {
        tvShow.bookmarked = newState
        mMovieDao.updateTvShow(tvShow)
    }
}