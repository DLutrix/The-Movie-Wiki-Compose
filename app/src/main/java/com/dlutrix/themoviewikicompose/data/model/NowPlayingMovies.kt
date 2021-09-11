package com.dlutrix.themoviewikicompose.data.model

import com.google.gson.annotations.SerializedName

data class NowPlayingMovies(
    @SerializedName("dates")
    val dates: Dates?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val movies: List<Result>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)
