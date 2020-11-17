package com.example.suantony.moviecatalogue.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tvshowentity")
data class TvShowEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieId")
    var movieId: Int?,

    @ColumnInfo(name = "title")
    var title: String?,

    @ColumnInfo(name = "date")
    var date: String?,

    @ColumnInfo(name = "score")
    var score: Double?,

    @ColumnInfo(name = "overview")
    var overview: String?,

    @ColumnInfo(name = "bookmarked")
    var bookmarked: Boolean = false,

    @ColumnInfo(name = "image")
    var image: String?
)