package com.example.suantony.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.suantony.moviecatalogue.data.source.local.entity.MovieEntity
import com.example.suantony.moviecatalogue.data.source.local.entity.TvShowEntity


@Dao
interface MovieDao {
    @Query("SELECT * FROM movieentity")
    fun getMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM tvshowentity")
    fun getTvShow(): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM movieentity where bookmarked = 1")
    fun getBookmarkedMovie(): DataSource.Factory<Int,MovieEntity>

    @Query("SELECT * FROM tvshowentity where bookmarked = 1")
    fun getBookmarkedTvShow(): DataSource.Factory<Int,TvShowEntity>

    @Transaction
    @Query("SELECT * FROM movieentity WHERE movieId = :movieId")
    fun getMovieById(movieId: String): LiveData<MovieEntity>

    @Transaction
    @Query("SELECT * FROM tvshowentity WHERE movieId = :tvShowId")
    fun getTvShowById(tvShowId: String): LiveData<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNovies(courses: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(courses: List<TvShowEntity>)

    @Update
    fun updateMovie(course: MovieEntity)

    @Update
    fun updateTvShow(module: TvShowEntity)

}