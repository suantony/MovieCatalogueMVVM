package com.example.suantony.moviecatalogue.ui.detail

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.suantony.moviecatalogue.data.source.MovieRepository
import com.example.suantony.moviecatalogue.data.source.local.entity.MovieEntity
import com.example.suantony.moviecatalogue.data.source.local.entity.TvShowEntity

class DetailMovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private var id: Int? = null

    fun setSelectedMovie(movieId: Int) {
        this.id = movieId
    }

    fun getMovieDetail(): LiveData<MovieEntity> = movieRepository.getMovieDetail(id)

    fun getTvShowDetail(): LiveData<TvShowEntity> = movieRepository.getTvShowDetail(id)

    fun setMovieBookmark(movie: MovieEntity?) {
        if (movie != null) {
            val newState = !movie.bookmarked
            movieRepository.setMovieBookmark(movie, newState)
        }
    }

    fun setTvShowBookmark(tvShow: TvShowEntity?) {
        if (tvShow != null) {
            val newState = !tvShow.bookmarked
            movieRepository.setTvShowBookmark(tvShow, newState)
        }
    }
}