package com.aldiwildan.moviecatalogue.utils

import android.content.Context
import com.aldiwildan.moviecatalogue.data.source.remote.response.MovieResponse
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class JsonHelper(private val context: Context) {

    private fun parseFileToString(filename: String): String? {
        return try {
            val `is` = context.assets.open(filename)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    fun loadMovies(): ArrayList<MovieResponse> {
        val list = ArrayList<MovieResponse>()
        try {
            val responseObject = JSONObject(parseFileToString("MovieResponses.json").toString())
            val listArray = responseObject.getJSONArray("movies")

            for (i in 0 until listArray.length()) {
                val movie = listArray.getJSONObject(i)

                val id = movie.getInt("id")
                val title = movie.getString("title")
                val overview = movie.getString("overview")
                val rating = movie.getDouble("rating")
                val release = movie.getString("release")
                val poster = movie.getString("poster")

                val movieResponse = MovieResponse(id, title, overview, rating, release, poster)
                list.add(movieResponse)
            }

        } catch (ex: JSONException) {
            ex.printStackTrace()
        }

        return list
    }

    fun loadTvShows(): ArrayList<MovieResponse> {
        val list = ArrayList<MovieResponse>()
        try {
            val responseObject = JSONObject(parseFileToString("TvShowResponses.json").toString())
            val listArray = responseObject.getJSONArray("tvshows")

            for (i in 0 until listArray.length()) {
                val movie = listArray.getJSONObject(i)

                val id = movie.getInt("id")
                val title = movie.getString("title")
                val overview = movie.getString("overview")
                val rating = movie.getDouble("rating")
                val release = movie.getString("release")
                val poster = movie.getString("poster")

                val movieResponse = MovieResponse(id, title, overview, rating, release, poster)
                list.add(movieResponse)
            }

        } catch (ex: JSONException) {
            ex.printStackTrace()
        }

        return list
    }
}