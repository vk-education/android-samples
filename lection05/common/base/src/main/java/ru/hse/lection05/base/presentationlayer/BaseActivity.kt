package ru.hse.lection05.base.presentationlayer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import ru.hse.lection05.api.giphy.objects.Item
import ru.hse.lection05.base.R
import ru.hse.lection05.base.presentationlayer.adapters.ItemAdapter


abstract class BaseActivity : AppCompatActivity(), ItemAdapter.IListener {
    companion object {
        const val QUERY_DELAY = 600L
    }


    protected val layout by lazy { findViewById<CoordinatorLayout>(R.id.layout) }
    protected val refresher by lazy { findViewById<SwipeRefreshLayout>(R.id.refresher) }
    protected val recycler by lazy { findViewById<RecyclerView>(R.id.recycler) }
    protected val search by lazy { findViewById<EditText>(R.id.search) }

    protected val itemAdapter by lazy { ItemAdapter(layoutInflater, this) }

    protected val mainHandler = Handler(Looper.getMainLooper())
    protected val queryUpdater = UpdateQuery()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_giphy)

        refresher.setOnRefreshListener(::refresh)
        search.addTextChangedListener(OnSearchListener())

        recycler.apply {
            val colCount = resources.getInteger(R.integer.giphy_grid_cols)

            adapter = itemAdapter
            layoutManager = StaggeredGridLayoutManager(colCount, RecyclerView.VERTICAL)
        }
    }

    override fun onItemClicked(view: View, item: Item) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
        startActivity(intent)
    }


    protected fun showError(message: String?) {
        val fixedMessage = when(message.isNullOrBlank()) {
            true -> getString(R.string.text_giphy_error_default)
            false -> message
        }

        Snackbar.make(layout, fixedMessage, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.caption_giphy_error_retry) { refresh() }
            .show()
    }


    protected abstract fun queryChanged(query: String)
    protected abstract fun refresh()


    inner class OnSearchListener: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // nothing to do
        }

        override fun afterTextChanged(s: Editable?) {
            // nothing to do
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            mainHandler.removeCallbacksAndMessages(null)
            mainHandler.postDelayed(queryUpdater, QUERY_DELAY)
        }
    }

    inner class UpdateQuery: Runnable {
        override fun run() {
            queryChanged(search.text.toString())
        }
    }
}