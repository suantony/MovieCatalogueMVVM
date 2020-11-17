package com.example.suantony.moviecatalogue.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.suantony.moviecatalogue.R
import com.example.suantony.moviecatalogue.data.source.local.entity.MovieEntity
import com.example.suantony.moviecatalogue.data.source.local.entity.TvShowEntity
import com.example.suantony.moviecatalogue.utils.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.content_detail_movie_activity.*

class DetailMovieActivity : AppCompatActivity() {

    internal lateinit var viewModel: DetailMovieViewModel
    private var state: String? = null
    private var menu: Menu? = null
    private var movieDetail: MovieEntity? = null
    private var tvshowDetail: TvShowEntity? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie_activity)
        setSupportActionBar(findViewById(R.id.toolbar))

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(
            this,
            factory
        )[DetailMovieViewModel::class.java]

        val extra = intent.extras
        if (extra != null) {
            val movieId = extra.getInt(EXTRA_MOVIE)
            state = extra.getString(EXTRA_STATE)

            viewModel.setSelectedMovie(movieId)

            if (state != null) {
                progress_bar.visibility = View.VISIBLE

                if (state == MOVIE_STATE) {
                    viewModel.getMovieDetail()
                        .observe(this, Observer { movie ->
                            movieDetail = movie
                            populateMovie(movie)
                        })
                } else {
                    viewModel.getTvShowDetail()
                        .observe(this, Observer { tvshow ->
                            tvshowDetail = tvshow
                            populateTvShow(tvshow)
                        })

                }
            }
        }
    }

    private fun populateMovie(movie: MovieEntity) {
        val popularity = "${resources.getString(R.string.popularity)} ${movie.score}"

        progress_bar.visibility = View.GONE

        text_title.text = movie.title
        text_date.text = movie.date
        text_overview.text = movie.overview
        text_score.text = popularity
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movie.image}")
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(image_poster)
    }

    private fun populateTvShow(tvshow: TvShowEntity) {
        val popularity = "${resources.getString(R.string.popularity)} ${tvshow.score}"

        progress_bar.visibility = View.GONE

        text_title.text = tvshow.title
        text_date.text = tvshow.date
        text_overview.text = tvshow.overview
        text_score.text = popularity
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${tvshow.image}")
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(image_poster)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu

        if (state == MOVIE_STATE) {
            viewModel.getMovieDetail()
                .observe(this, Observer { movie ->
                    val bookmarkState = movie.bookmarked
                    setBookmarkState(bookmarkState)
                })
        } else {
            viewModel.getTvShowDetail()
                .observe(this, Observer { tvshow ->
                    populateTvShow(tvshow)
                    val bookmarkState = tvshow.bookmarked
                    setBookmarkState(bookmarkState)
                })
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_bookmark) {
            if (state == MOVIE_STATE) {
                viewModel.setMovieBookmark(movieDetail)
            } else {
                viewModel.setTvShowBookmark(tvshowDetail)
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBookmarkState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_bookmark)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_white_bookmarked)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_white_bookmark)
        }
    }


    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_STATE = "extra_state"

        const val MOVIE_STATE = "movie"
    }
}