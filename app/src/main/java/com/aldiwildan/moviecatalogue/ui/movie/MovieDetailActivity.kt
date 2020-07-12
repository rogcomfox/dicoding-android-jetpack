package com.aldiwildan.moviecatalogue.ui.movie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aldiwildan.moviecatalogue.R
import com.aldiwildan.moviecatalogue.data.source.local.entity.MovieEntity
import com.aldiwildan.moviecatalogue.utils.Constants
import com.aldiwildan.moviecatalogue.viewmodel.ViewModelFactory
import com.aldiwildan.moviecatalogue.vo.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    private var menu: Menu? = null

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
    }

    private val viewModel by lazy {
        val factory = ViewModelFactory.getInstance(this)
        ViewModelProvider(this, factory)[MovieViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras
        if (extras != null) {
            val movieId = extras.getString(EXTRA_MOVIE_ID)
            if (movieId != null) {
                viewModel.setSelectedMovie(movieId.toString())
                viewModel.getMovie.observe(this, Observer { movie ->
                    if (movie != null) {
                        when (movie.status) {
                            Status.LOADING -> {
                                pb_detail.visibility = View.VISIBLE
                                tv_loading.visibility = View.VISIBLE
                            }
                            Status.SUCCESS -> {
                                pb_detail.visibility = View.GONE
                                tv_loading.visibility = View.GONE
                                cl_detail.visibility = View.VISIBLE
                                populateMovie(movie.data)
                                movie.data?.favorite?.let { setFavoriteState(it) }
                            }
                            Status.ERROR -> {
                                pb_detail.visibility = View.GONE
                                Toast.makeText(
                                    applicationContext,
                                    getString(R.string.error),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                })
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_item, menu)
        this.menu = menu
        if (intent.extras?.getString(EXTRA_MOVIE_ID) != null) {
            viewModel.getMovie.observe(this, Observer { movie ->
                if (movie != null) {
                    when (movie.status) {
                        Status.LOADING -> {
                            pb_detail.visibility = View.VISIBLE
                            tv_loading.visibility = View.VISIBLE
                        }
                        Status.SUCCESS -> if (movie.data != null) {
                            pb_detail.visibility = View.GONE
                            tv_loading.visibility = View.GONE
                            cl_detail.visibility = View.VISIBLE
                            val state = movie.data.favorite
                            setFavoriteState(state)
                        }
                        Status.ERROR -> {
                            pb_detail.visibility = View.GONE
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_favorite -> {
                viewModel.setMovieFavorite()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFavoriteState(isFavorite: Boolean) {
        if (menu == null) return
        val favoriteItem = menu?.findItem(R.id.menu_item_favorite)
        if (isFavorite) {
            favoriteItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorited)
        } else {
            favoriteItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun populateMovie(movie: MovieEntity?) {
        tv_item_title.text = movie!!.title
        tv_item_overview_value.text = movie.overview
        tv_item_rating.text = movie.rating.toString()
        tv_item_release.text = "Released at " + movie.release

        Glide.with(this)
            .load(Constants.IMG_URL + movie.poster)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
            .error(R.drawable.ic_error)
            .into(img_poster)
    }
}
