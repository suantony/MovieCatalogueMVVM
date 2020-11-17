package com.example.suantony.moviecatalogue.ui.favorite.tvshowFavorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.suantony.moviecatalogue.data.source.MovieRepository
import com.example.suantony.moviecatalogue.data.source.local.entity.TvShowEntity

class TvShowFavoriteViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getBookmarkTvShow(): LiveData<PagedList<TvShowEntity>> = movieRepository.getBookmarkedTvShow()

    fun setTvShowBookmark(tvShow: TvShowEntity?) {
        if (tvShow != null) {
            val newState = !tvShow.bookmarked
            movieRepository.setTvShowBookmark(tvShow, newState)
        }
    }
}
