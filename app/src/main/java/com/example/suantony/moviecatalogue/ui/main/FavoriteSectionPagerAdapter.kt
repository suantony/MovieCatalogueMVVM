package com.example.suantony.moviecatalogue.ui.main

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.suantony.moviecatalogue.R
import com.example.suantony.moviecatalogue.ui.favorite.moviesFavorite.MoviesFavoriteFragment
import com.example.suantony.moviecatalogue.ui.favorite.tvshowFavorite.TvShowFavoriteFragment

class FavoriteSectionPagerAdapter(private val mContext: Context?, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> MoviesFavoriteFragment()
            1 -> TvShowFavoriteFragment()
            else -> Fragment()
        }

    override fun getPageTitle(position: Int): CharSequence? =
        mContext?.resources?.getString(TAB_TITLES[position])

    override fun getCount(): Int = TAB_TITLES.size

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.movie, R.string.tvshow)
    }
}