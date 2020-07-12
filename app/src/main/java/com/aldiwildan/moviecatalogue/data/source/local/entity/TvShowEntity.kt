package com.aldiwildan.moviecatalogue.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tvShowEntities")
data class TvShowEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "title")
    val title: String? = "",

    @ColumnInfo(name = "overview")
    val overview: String? = "",

    @ColumnInfo(name = "rating")
    val rating: Double? = 0.0,

    @ColumnInfo(name = "release")
    val release: String? = "",

    @ColumnInfo(name = "poster")
    val poster: String? = "",

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false
)
