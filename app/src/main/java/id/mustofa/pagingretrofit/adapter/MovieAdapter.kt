/*
 * Habib Mustofa on 16/10/19
 * https://mustofa.id
 */
package id.mustofa.pagingretrofit.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.mustofa.pagingretrofit.R
import id.mustofa.pagingretrofit.data.Movie
import id.mustofa.pagingretrofit.data.NetworkState
import id.mustofa.pagingretrofit.ext.inflate

object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
  override fun areItemsTheSame(old: Movie, new: Movie) = old.id == new.id
  override fun areContentsTheSame(old: Movie, new: Movie) = old == new
}

class MovieAdapter(private val retryCallback: () -> Unit) :
  PagedListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallback) {

  private val movieView = R.layout.item_movie
  private val stateView = R.layout.item_state
  private var networkState: NetworkState? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val view = parent.inflate(viewType)
    return when (viewType) {
      movieView -> MovieViewHolder(view)
      stateView -> StateViewHolder(view, retryCallback)
      else -> throw IllegalArgumentException("Unknown view type: $viewType")
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (holder) {
      is MovieViewHolder -> holder.setMovie(getItem(position))
      is StateViewHolder -> holder.setState(networkState)
    }
  }

  override fun getItemViewType(position: Int): Int {
    return if (hasExtraRow() && position == itemCount - 1) stateView else movieView
  }

  override fun getItemCount(): Int {
    return super.getItemCount() + if (hasExtraRow()) 1 else 0
  }

  private fun hasExtraRow(): Boolean {
    return networkState != null && networkState != NetworkState.LOADED
  }

  fun setNetworkState(newNetworkState: NetworkState?) {
    val previousState = this.networkState
    val hadExtraRow = hasExtraRow()
    this.networkState = newNetworkState
    val hasExtraRow = hasExtraRow()
    if (hadExtraRow != hasExtraRow) {
      if (hadExtraRow) {
        notifyItemRemoved(super.getItemCount())
      } else {
        notifyItemInserted(super.getItemCount())
      }
    } else if (hasExtraRow && previousState != newNetworkState) {
      notifyItemChanged(itemCount - 1)
    }
  }
}