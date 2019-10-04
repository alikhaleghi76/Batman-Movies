package ali.khaleghi.batman.service.model.detail

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DetailResponse(

	@SerializedName("Metascore")
	val metaScore: String? = null,

	@SerializedName("BoxOffice")
	val boxOffice: String? = null,

	@SerializedName("Website")
	val website: String? = null,

	@SerializedName("imdbRating")
	val imdbRating: String? = null,

	@SerializedName("imdbVotes")
	val imdbVotes: String? = null,

	@SerializedName("Ratings")
	val ratings: List<RatingsItem?>? = null,

	@SerializedName("Runtime")
	val runtime: String? = null,

	@SerializedName("Language")
	val language: String? = null,

	@SerializedName("Rated")
	val rated: String? = null,

	@SerializedName("Production")
	val production: String? = null,

	@SerializedName("Released")
	val released: String? = null,

	@SerializedName("imdbID")
	val imdbID: String? = null,

	@SerializedName("Plot")
	val plot: String? = null,

	@SerializedName("Director")
	val director: String? = null,

	@SerializedName("Title")
	val title: String? = null,

	@SerializedName("Actors")
	val actors: String? = null,

	@SerializedName("Response")
	val response: String? = null,

	@SerializedName("Type")
	val type: String? = null,

	@SerializedName("Awards")
	val awards: String? = null,

	@SerializedName("DVD")
	val dVD: String? = null,

	@SerializedName("Year")
	val year: String? = null,

	@SerializedName("Poster")
	val poster: String? = null,

	@SerializedName("Country")
	val country: String? = null,

	@SerializedName("Genre")
	val genre: String? = null,

	@SerializedName("Writer")
	val writer: String? = null,

	@SerializedName("totalSeasons")
	val totalSeasons: Int? = null

): Serializable