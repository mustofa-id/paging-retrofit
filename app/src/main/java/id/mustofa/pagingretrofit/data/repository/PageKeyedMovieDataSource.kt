/*
 * Habib Mustofa on 16/10/19
 * https://mustofa.id
 */
package id.mustofa.pagingretrofit.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import id.mustofa.pagingretrofit.data.Movie
import id.mustofa.pagingretrofit.data.NetworkState
import id.mustofa.pagingretrofit.data.source.remote.MovieService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PageKeyedMovieDataSource(
  private val movieService: MovieService,
  private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Movie>() {

  private var retry: (() -> Any)? = null

  /**
   * There is no sync on the state because paging will always call loadInitial first then wait
   * for it to return some success value before calling loadAfter.
   */
  val networkState = MutableLiveData<NetworkState>()
  val initialLoad = MutableLiveData<NetworkState>()

  fun retryAllFailed() {
    val prevRetry = retry
    retry = null
    prevRetry?.let {
      scope.launch { it.invoke() }
    }
  }

  override fun loadInitial(
    params: LoadInitialParams<Int>,
    callback: LoadInitialCallback<Int, Movie>
  ) {
    scope.launch {
      networkState.postValue(NetworkState.LOADING)
      initialLoad.postValue(NetworkState.LOADING)

      try {
        val prev = 1
        val next = prev + 1
        val response = movieService.discover(prev)
        val data = response.body()
        val results = data?.results ?: emptyList()
        retry = null
        networkState.postValue(NetworkState.LOADED)
        initialLoad.postValue(NetworkState.LOADED)
        callback.onResult(results, prev, next)
      } catch (e: Exception) {
        retry = { loadInitial(params, callback) }
        val error = NetworkState.error(e.message ?: "Unknown error")
        networkState.postValue(error)
        initialLoad.postValue(error)
        Log.e(javaClass.name, "loadInitial: ${e.message}")
      }
    }
  }

  override fun loadBefore(
    params: LoadParams<Int>,
    callback: LoadCallback<Int, Movie>
  ) {
    // ignore
  }

  override fun loadAfter(
    params: LoadParams<Int>,
    callback: LoadCallback<Int, Movie>
  ) {
    scope.launch {
      networkState.postValue(NetworkState.LOADING)
      try {
        val response = movieService.discover(params.key)
        if (response.isSuccessful) {
          val items = response.body()?.results ?: emptyList()
          val adjacent = params.key + 1
          retry = null
          callback.onResult(items, adjacent)
          networkState.postValue(NetworkState.LOADED)
        }
      } catch (e: Exception) {
        retry = { loadAfter(params, callback) }
        networkState.postValue(NetworkState.error(e.message ?: "Unknown error"))
        Log.e(javaClass.name, "loadAfter: ${e.message}")
      }
    }
  }
}