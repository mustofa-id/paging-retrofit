/*
 * Habib Mustofa on 16/10/19
 * https://mustofa.id
 */
package id.mustofa.pagingretrofit.data.repository

import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import id.mustofa.pagingretrofit.data.Movie
import id.mustofa.pagingretrofit.data.Result
import id.mustofa.pagingretrofit.data.source.MovieRepository
import id.mustofa.pagingretrofit.data.source.remote.MovieService
import kotlinx.coroutines.CoroutineScope

class DefaultMovieRepository(
  private val service: MovieService,
  private val scope: CoroutineScope
) : MovieRepository {

  override fun getDiscoverMovies(pageSize: Int): Result<Movie> {
    val dataSourceFactory = MovieDataSourceFactory(service, scope)

    val config = PagedList.Config.Builder()
      .setPageSize(pageSize)
      .setEnablePlaceholders(true)
      .build()

    return Result(
      pagedList = LivePagedListBuilder<Int, Movie>(dataSourceFactory, config).build(),
      networkState = Transformations.switchMap(dataSourceFactory.sourceLiveData) { it.networkState },
      refreshState = Transformations.switchMap(dataSourceFactory.sourceLiveData) { it.initialLoad },
      retry = { dataSourceFactory.sourceLiveData.value?.retryAllFailed() },
      refresh = { dataSourceFactory.sourceLiveData.value?.invalidate() }
    )
  }
}