package com.example.suantony.moviecatalogue.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowResponse(
	@field:SerializedName("results")
	val results: List<TvShowResponseItem?>? = null
)

data class TvShowResponseItem(
	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("original_name")
	val originalName: String? = null,

	@field:SerializedName("first_air_date")
	val firstAirDate: String? = null,

	@field:SerializedName("popularity")
	val popularity: Double? = null,

	@field:SerializedName("overview")
	val overview: String? = null,

	@field:SerializedName("poster_path")
	val posterPath: String? = null

)
