/*
 * Habib Mustofa on 16/10/19
 * https://mustofa.id
 */
package id.mustofa.pagingretrofit.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class Result<T>(
  val pagedList: LiveData<PagedList<T>>,
  val networkState: LiveData<NetworkState>,
  val refreshState: LiveData<NetworkState>,
  val refresh: () -> Unit,
  val retry: () -> Unit
)
