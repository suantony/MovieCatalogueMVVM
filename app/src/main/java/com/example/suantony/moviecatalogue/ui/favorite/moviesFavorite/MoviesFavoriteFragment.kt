package com.example.suantony.moviecatalogue.ui.favorite.moviesFavorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suantony.moviecatalogue.R
import com.example.suantony.moviecatalogue.utils.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFavoriteFragment : Fragment() {
    lateinit var viewModel: MoviesFavoriteViewModel
    lateinit var movieBookmarkAdapter: MoviesFavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(rv_movie)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(context)
            viewModel = ViewModelProvider(this, factory)[MoviesFavoriteViewModel::class.java]

            movieBookmarkAdapter = MoviesFavoriteAdapter()

            viewModel.getBookmarkMovie().observe(requireActivity(), Observer { movie ->
                movieBookmarkAdapter.submitList(movie)
            })

            with(rv_movie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = movieBookmarkAdapter
            }
        }
    }

    override fun onResume() {
        viewModel.getBookmarkMovie().observe(requireActivity(), Observer { movie ->
            movieBookmarkAdapter.submitList(movie)
        })
        super.onResume()
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val movieEntity = movieBookmarkAdapter.getSwipedData(swipedPosition)
                viewModel.setMovieBookmark(movieEntity)

                val snackbar = Snackbar.make(
                    view as View,
                    getString(R.string.message_undo),
                    Snackbar.LENGTH_LONG
                )
                snackbar.setAction(R.string.message_ok) { v ->
                    viewModel.setMovieBookmark(movieEntity)
                }
                snackbar.show()
            }
        }

    })

}
