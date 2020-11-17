package com.example.suantony.moviecatalogue.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.suantony.moviecatalogue.data.source.local.entity.MovieEntity

class MovieDiffCallback (private val mOldMovieList: List<MovieEntity>, private val mNewMovieList: List<MovieEntity>): DiffUtil.Callback(){
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldMovieList[oldItemPosition].movieId == mNewMovieList[newItemPosition].movieId
    }

    override fun getOldListSize(): Int {
        return mOldMovieList.size
    }

    override fun getNewListSize(): Int {
        return mNewMovieList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMovie = mOldMovieList[oldItemPosition]
        val newMovie = mNewMovieList[newItemPosition]
        return oldMovie.title == newMovie.title && oldMovie.overview == newMovie.overview
    }

}