/*
 * Habib Mustofa on 16/10/19
 * https://mustofa.id
 */
package id.mustofa.pagingretrofit.data.source.remote

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TmdbApi {

  private const val baseUrl = "https://api.themoviedb.org/3/"

  val movieService: MovieService by lazy {
    val retrofit = Retrofit.Builder()
      .baseUrl(baseUrl)
      .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
      .build()
    return@lazy retrofit.create(MovieService::class.java)
  }
}