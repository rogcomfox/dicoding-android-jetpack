package com.aldiwildan.moviecatalogue.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("original_title")
    val title: String?,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("vote_average")
    val rating: Double?,

    @SerializedName("release_date")
    val release: String?,

    @SerializedName("poster_path")
    val poster: String?
) : Parcelable