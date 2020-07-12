package com.aldiwildan.moviecatalogue.utils

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.aldiwildan.moviecatalogue.R
import com.aldiwildan.moviecatalogue.ui.favorite.movie.FavoriteMovieFragment
import com.aldiwildan.moviecatalogue.ui.favorite.tvshow.FavoriteTvShowFragment
import com.aldiwildan.moviecatalogue.ui.movie.MovieFragment
import com.aldiwildan.moviecatalogue.ui.tvshow.TvShowFragment

class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.tab_movie,
            R.string.tab_tv_show,
            R.string.tab_fav_movie,
            R.string.tab_fav_tv_show
        )
    }

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> MovieFragment()
            1 -> TvShowFragment()
            2 -> FavoriteMovieFragment()
            3 -> FavoriteTvShowFragment()
            else -> Fragment()
        }

    override fun getPageTitle(position: Int): CharSequence? = mContext.resources.getString(
        TAB_TITLES[position]
    )

    override fun getCount(): Int = TAB_TITLES.size

}