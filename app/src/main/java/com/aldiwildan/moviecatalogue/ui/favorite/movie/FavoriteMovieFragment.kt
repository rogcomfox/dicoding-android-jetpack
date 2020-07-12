package com.aldiwildan.moviecatalogue.ui.favorite.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aldiwildan.moviecatalogue.R
import com.aldiwildan.moviecatalogue.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.favorite_movie_fragment.*

class FavoriteMovieFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_movie_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[FavoriteMovieViewModel::class.java]

            val favoriteMovieAdapter = FavoriteMovieAdapter()
            viewModel.getMovies().observe(viewLifecycleOwner, Observer { movies ->
                progress_bar.visibility = View.GONE

                if (movies.isEmpty()) {
                    tv_no_fav_movie.visibility = View.VISIBLE
                } else {
                    favoriteMovieAdapter.submitList(movies)
                    favoriteMovieAdapter.notifyDataSetChanged()
                }

            })

            with(rv_favorite_movies) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = favoriteMovieAdapter
            }
        }
    }

}