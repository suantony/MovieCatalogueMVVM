package com.example.suantony.moviecatalogue.ui.favorite.tvshowFavorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.suantony.moviecatalogue.data.source.MovieRepository
import com.example.suantony.moviecatalogue.data.source.local.entity.MovieEntity
import com.example.suantony.moviecatalogue.data.source.local.entity.TvShowEntity
import com.example.suantony.moviecatalogue.ui.favorite.moviesFavorite.MoviesFavoriteViewModel
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowFavoriteViewModelTest {
    private lateinit var viewModel: TvShowFavoriteViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<PagedList<TvShowEntity>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowEntity>

    @Before
    fun setUp() {
        viewModel = TvShowFavoriteViewModel(movieRepository)
    }

    @Test
    fun getBookmarkedTvShow() {
        val dummyTvShow = pagedList
        Mockito.`when`(dummyTvShow.size).thenReturn(5)
        val tvshows = MutableLiveData<PagedList<TvShowEntity>>()
        tvshows.value = dummyTvShow

        Mockito.`when`(movieRepository.getBookmarkedTvShow()).thenReturn(tvshows)
        val tvShowEntities = viewModel.getBookmarkTvShow().value
        verify<MovieRepository>(movieRepository).getBookmarkedTvShow()
        assertNotNull(tvShowEntities)
        assertEquals(5, tvShowEntities?.size)

        viewModel.getBookmarkTvShow().observeForever(observer)
        verify(observer).onChanged(dummyTvShow)
    }
}