package com.example.suantony.moviecatalogue.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.suantony.moviecatalogue.data.source.local.LocalDataSource
import com.example.suantony.moviecatalogue.data.source.local.entity.MovieEntity
import com.example.suantony.moviecatalogue.data.source.local.entity.TvShowEntity
import com.example.suantony.moviecatalogue.data.source.remote.RemoteDataSource
import com.example.suantony.moviecatalogue.utils.AppExecutors
import com.example.suantony.moviecatalogue.utils.DataDummy
import com.example.suantony.moviecatalogue.utils.LiveDataTestUtil
import com.example.suantony.moviecatalogue.utils.PagedListUtils
import com.example.suantony.moviecatalogue.utils.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class MovieRepositoryTest {
    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val movieRepository = FakeMovieRepository(remote,local,appExecutors)

    private val movieResponseItem = DataDummy.generateRemoteDummyMovies()
    private val movieId = movieResponseItem[0].id
    private val tvShowResponseItem = DataDummy.generateRemoteDummyTvShow()
    private val tvShowId = tvShowResponseItem[0].id


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getAllMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovies()).thenReturn(dataSourceFactory)
        movieRepository.getAllMovies()

        val movieEntities = Resource.success(PagedListUtils.mockPagedList(DataDummy.generateDummyMovie()))
        verify(local).getAllMovies()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponseItem.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getAllTvShow() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getAllTvShow()).thenReturn(dataSourceFactory)
        movieRepository.getAllTvShow()

        val tvShowEntities = Resource.success(PagedListUtils.mockPagedList(DataDummy.generateDummyTvShow()))
        verify(local).getAllTvShow()
        assertNotNull(tvShowEntities.data)
        assertEquals(tvShowResponseItem.size.toLong(), tvShowEntities.data?.size?.toLong())
    }

    @Test
    fun getMovieDetail() {
        val dummyMovie = MutableLiveData<MovieEntity>()
        dummyMovie.value = DataDummy.generateDummyMovie()[0]
        `when`(local.getMovieWithId(movieId.toString())).thenReturn(dummyMovie)

        val movieEntities = LiveDataTestUtil.getValue(movieRepository.getMovieDetail(movieId))
        verify(local).getMovieWithId(movieId.toString())
        assertNotNull(movieEntities)
        assertEquals(movieResponseItem[0].title, movieEntities.title)
    }

    @Test
    fun getTvShowDetail() {
        val dummyTvShow = MutableLiveData<TvShowEntity>()
        dummyTvShow.value = DataDummy.generateDummyTvShow()[0]
        `when`(local.getTvShowWithId(tvShowId.toString())).thenReturn(dummyTvShow)

        val tvShowEntities = LiveDataTestUtil.getValue(movieRepository.getTvShowDetail(tvShowId))
        verify(local).getTvShowWithId(tvShowId.toString())
        assertNotNull(tvShowEntities)
        assertEquals(tvShowResponseItem[0].originalName, tvShowEntities.title)
    }

    @Test
    fun getBookmarkedMovies(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getBookmarkedMovie()).thenReturn(dataSourceFactory)
        movieRepository.getBookmarkedMovies()

        val movieEntities =  PagedListUtils.mockPagedList(DataDummy.generateDummyTvShow())
        verify(local).getBookmarkedMovie()
        assertNotNull(movieEntities)
        assertEquals(movieResponseItem.size.toLong(), movieEntities.size.toLong())
    }

    @Test
    fun getBookmarkedTvShows(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getBookmarkedTvShow()).thenReturn(dataSourceFactory)
        movieRepository.getBookmarkedTvShow()

        val tvShowEntities = PagedListUtils.mockPagedList(DataDummy.generateDummyTvShow())
        verify(local).getBookmarkedTvShow()
        assertNotNull(tvShowEntities)
        assertEquals(movieResponseItem.size.toLong(), tvShowEntities.size.toLong())
    }

}