/*
 * Habib Mustofa on 16/10/19
 * https://mustofa.id
 */
package id.mustofa.pagingretrofit.data

import com.google.gson.annotations.SerializedName

data class Movie(

  val id: Long,

  @SerializedName(value = "title")
  val title: String = "",

  val overview: String = "",

  @SerializedName(value = "release_date")
  val releaseDate: String = "",

  @SerializedName("vote_average")
  val voteAverage: Float = 0F
)

data class MovieResponse(
  val page: Int,
  val totalResult: Int,
  val totalPages: Int,
  val results: List<Movie>
)