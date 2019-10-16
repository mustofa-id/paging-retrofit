/*
 * Habib Mustofa on 16/10/19
 * https://mustofa.id
 */
package id.mustofa.pagingretrofit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.mustofa.pagingretrofit.data.repository.DefaultMovieRepository
import id.mustofa.pagingretrofit.data.source.remote.TmdbApi

class MainViewModel : ViewModel() {

  private val repo = DefaultMovieRepository(TmdbApi.movieService, viewModelScope)
  private val loader = MutableLiveData<Unit>()
  private val repoResult = map(loader) { repo.getDiscoverMovies(20) }

  val movies = switchMap(repoResult) { it.pagedList }
  val networkState = switchMap(repoResult) { it.networkState }
  val refreshState = switchMap(repoResult) { it.refreshState }

  init {
    loader.postValue(Unit)
  }

  fun refresh() {
    repoResult.value?.refresh?.invoke()
  }

  fun retry() {
    repoResult.value?.retry?.invoke()
  }
}