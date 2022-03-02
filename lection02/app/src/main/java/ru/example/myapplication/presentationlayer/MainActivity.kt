package ru.example.myapplication.presentationlayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.example.myapplication.R
import ru.example.myapplication.objects.Item
import ru.example.myapplication.presentationlayer.adapters.GifAdapter

class MainActivity : AppCompatActivity() {
    protected val recycler by lazy { findViewById<RecyclerView>(R.id.recycer) }
    protected val stub by lazy { findViewById<TextView>(R.id.stub) }

    protected val viewModel by viewModels<MainViewModel>()
    protected val gifAdapter by lazy { GifAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        recycler.apply {
            adapter = gifAdapter
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        }

        lifecycleScope.launch {
            viewModel.state().collectLatest(::applyState)
        }
    }


    protected fun applyState(state: States<List<Item>>) {
        stub.isVisible = state !is Success
        stub.setOnClickListener(null)

        when(state) {
            is None -> {
                stub.text = null
                viewModel.load()
            }

            is Pending -> {
                stub.text = "Pending"
            }

            is Fail -> {
                stub.text = "Error: ${state.error}"
                stub.setOnClickListener { viewModel.retry() }
            }

            is Success -> {
                stub.text = null
                gifAdapter.submitList(state.result)
            }
        }
    }
}