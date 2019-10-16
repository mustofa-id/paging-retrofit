/*
 * Habib Mustofa on 16/10/19
 * https://mustofa.id
 */
package id.mustofa.pagingretrofit.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layoutId: Int): View =
  LayoutInflater.from(context).inflate(layoutId, this, false)

fun View.visible(constraint: Boolean) {
  visibility = if (constraint) View.VISIBLE else View.GONE
}