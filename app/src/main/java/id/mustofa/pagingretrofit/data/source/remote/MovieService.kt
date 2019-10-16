/*
 * Habib Mustofa on 16/10/19
 * https://mustofa.id
 */
package id.mustofa.pagingretrofit.data.source.remote

import id.mustofa.pagingretrofit.BuildConfig
import id.mustofa.pagingretrofit.data.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

  @GET("discover/movie?api_key=${BuildConfig.MOVIEDB_API_KEY}")
  suspend fun discover(@Query("page") page: Int): Response<MovieResponse>
}