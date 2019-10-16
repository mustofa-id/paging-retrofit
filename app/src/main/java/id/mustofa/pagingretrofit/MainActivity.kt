package id.mustofa.pagingretrofit

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.mustofa.pagingretrofit.adapter.MovieAdapter
import id.mustofa.pagingretrofit.data.NetworkState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  private var hasData = false
  private val viewModel: MainViewModel by viewModels {
    object : ViewModelProvider.Factory {
      @Suppress("UNCHECKED_CAST")
      override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel() as T
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupList()
    setupRefresher()
  }

  private fun setupList() {
    val adapter = MovieAdapter { viewModel.retry() }
    mainList.layoutManager = LinearLayoutManager(this)
    mainList.adapter = adapter
    viewModel.networkState.observe(this, Observer { adapter.setNetworkState(it) })
    viewModel.movies.observe(this, Observer {
      adapter.submitList(it)
      hasData = !it.isNullOrEmpty()
    })
  }

  private fun setupRefresher() {
    mainContainer.setOnRefreshListener { viewModel.refresh() }
    viewModel.refreshState.observe(this, Observer {
      mainContainer.isRefreshing = it == NetworkState.LOADING && hasData
    })
  }
}
