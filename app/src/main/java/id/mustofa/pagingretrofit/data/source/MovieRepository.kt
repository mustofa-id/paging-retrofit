/*
 * Habib Mustofa on 16/10/19
 * https://mustofa.id
 */
package id.mustofa.pagingretrofit.data.source

import id.mustofa.pagingretrofit.data.Movie
import id.mustofa.pagingretrofit.data.Result

interface MovieRepository {

  fun getDiscoverMovies(pageSize: Int): Result<Movie>
}