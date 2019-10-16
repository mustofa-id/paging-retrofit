/*
 * Habib Mustofa on 16/10/19
 * https://mustofa.id
 */
package id.mustofa.pagingretrofit.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import id.mustofa.pagingretrofit.data.Movie
import id.mustofa.pagingretrofit.data.source.remote.MovieService
import kotlinx.coroutines.CoroutineScope

class MovieDataSourceFactory(
  private val movieService: MovieService,
  private val scope: CoroutineScope
) : DataSource.Factory<Int, Movie>() {

  val sourceLiveData = MutableLiveData<PageKeyedMovieDataSource>()

  override fun create(): DataSource<Int, Movie> {
    val source = PageKeyedMovieDataSource(movieService, scope)
    sourceLiveData.postValue(source)
    return source
  }
}