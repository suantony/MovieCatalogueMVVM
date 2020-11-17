package com.example.suantony.moviecatalogue.ui.favorite.moviesFavorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.suantony.moviecatalogue.data.source.MovieRepository
import com.example.suantony.moviecatalogue.data.source.local.entity.MovieEntity

class MoviesFavoriteViewModel(private val movieRepository: MovieRepository) : ViewModel(){
    fun getBookmarkMovie():LiveData<PagedList<MovieEntity>> = movieRepository.getBookmarkedMovies()

    fun setMovieBookmark(movie: MovieEntity?) {
        if (movie != null) {
            val newState = !movie.bookmarked
            movieRepository.setMovieBookmark(movie, newState)
        }
    }
}
