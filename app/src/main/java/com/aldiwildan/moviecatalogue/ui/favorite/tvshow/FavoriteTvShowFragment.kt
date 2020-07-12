package com.aldiwildan.moviecatalogue.ui.favorite.tvshow

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
import kotlinx.android.synthetic.main.favorite_movie_fragment.progress_bar
import kotlinx.android.synthetic.main.favorite_movie_fragment.tv_no_fav_movie
import kotlinx.android.synthetic.main.favorite_tv_show_fragment.*

class FavoriteTvShowFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_tv_show_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[FavoriteTvShowViewModel::class.java]

            val favoriteMovieAdapter = FavoriteTvShowAdapter()
            viewModel.getTvShows().observe(viewLifecycleOwner, Observer { tvShows ->
                progress_bar.visibility = View.GONE

                if (tvShows.isEmpty()) {
                    tv_no_fav_movie.visibility = View.VISIBLE
                } else {
                    favoriteMovieAdapter.submitList(tvShows)
                    favoriteMovieAdapter.notifyDataSetChanged()
                }

            })

            with(rv_favorite_tv_shows) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = favoriteMovieAdapter
            }
        }
    }

}