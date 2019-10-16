package id.mustofa.pagingretrofit.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import id.mustofa.pagingretrofit.data.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

  fun setMovie(movie: Movie?) {
    val pos = adapterPosition + 1
    itemView.apply {
      var title = movie?.title ?: "Loading..."
      title = "$pos - $title"
      val desc = movie?.let {
        "${it.releaseDate} \u272F ${it.voteAverage}\n${it.overview}"
      } ?: "Loading..."

      movieTitle.text = title
      movieDesc.text = desc
    }
  }
}