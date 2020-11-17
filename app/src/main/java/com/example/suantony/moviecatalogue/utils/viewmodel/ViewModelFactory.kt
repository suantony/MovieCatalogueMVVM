package com.example.suantony.moviecatalogue.utils.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.suantony.moviecatalogue.data.source.MovieRepository
import com.example.suantony.moviecatalogue.di.Injection
import com.example.suantony.moviecatalogue.ui.detail.DetailMovieViewModel
import com.example.suantony.moviecatalogue.ui.favorite.moviesFavorite.MoviesFavoriteViewModel
import com.example.suantony.moviecatalogue.ui.favorite.tvshowFavorite.TvShowFavoriteViewModel
import com.example.suantony.moviecatalogue.ui.movies.MoviesViewModel
import com.example.suantony.moviecatalogue.ui.tvshow.TvShowViewModel

class ViewModelFactory private constructor(private val mMovieRepository: MovieRepository) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context?): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context!!))
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MoviesViewModel::class.java) -> {
                MoviesViewModel(mMovieRepository) as T
            }

            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> {
                TvShowViewModel(mMovieRepository) as T
            }

            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
                DetailMovieViewModel(mMovieRepository) as T
            }

            modelClass.isAssignableFrom(MoviesFavoriteViewModel::class.java) -> {
                MoviesFavoriteViewModel(
                    mMovieRepository
                ) as T
            }

            modelClass.isAssignableFrom(TvShowFavoriteViewModel::class.java) -> {
                TvShowFavoriteViewModel(
                    mMovieRepository
                ) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }

}