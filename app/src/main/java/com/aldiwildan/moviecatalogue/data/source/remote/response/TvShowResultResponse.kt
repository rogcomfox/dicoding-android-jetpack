package com.aldiwildan.moviecatalogue.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowResultResponse(
    @SerializedName("results")
    val result: List<TvShowResponse>?
)