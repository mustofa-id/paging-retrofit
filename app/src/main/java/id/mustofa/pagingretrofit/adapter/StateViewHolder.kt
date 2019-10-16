package id.mustofa.pagingretrofit.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import id.mustofa.pagingretrofit.data.NetworkState
import id.mustofa.pagingretrofit.data.Status.FAILED
import id.mustofa.pagingretrofit.data.Status.RUNNING
import id.mustofa.pagingretrofit.ext.visible
import kotlinx.android.synthetic.main.item_state.view.*

class StateViewHolder(view: View, val retryCallback: () -> Unit) : RecyclerView.ViewHolder(view) {

  fun setState(state: NetworkState?) {
    with(itemView) {
      stateProgressBar.visible(state?.status == RUNNING)
      stateRetryBtn.visible(state?.status == FAILED)
      stateRetryBtn.setOnClickListener { retryCallback() }
      stateMessage.visible(state?.msg != null)
      stateMessage.text = state?.msg
    }
  }
}