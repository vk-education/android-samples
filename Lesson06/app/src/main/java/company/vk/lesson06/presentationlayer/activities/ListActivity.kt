package company.vk.lesson06.presentationlayer.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import company.vk.lesson06.R
import company.vk.lesson06.objects.Item
import company.vk.lesson06.presentationlayer.adapters.ItemAdapter
import company.vk.lesson06.presentationlayer.models.Fail
import company.vk.lesson06.presentationlayer.models.ItemViewModel
import company.vk.lesson06.presentationlayer.models.Pending
import company.vk.lesson06.presentationlayer.models.State
import company.vk.lesson06.presentationlayer.models.Success
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListActivity : AppCompatActivity(), ItemAdapter.IListener {
    protected val viewModel by viewModels<ItemViewModel>()
    protected val itemAdapter by lazy { ItemAdapter(this) }

    protected val progress by lazy { findViewById<View>(R.id.progress) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list)

        findViewById<RecyclerView>(R.id.recycler).apply {
            adapter = itemAdapter
            layoutManager = GridLayoutManager(context, COL_COUNT)
        }

        lifecycleScope.launch {
            viewModel.state().collectLatest(::applyState)
        }

        viewModel.load()
    }

    override fun onItemClicked(item: Item) {
        Toast.makeText(this, "Clicked title: ${item.name}", Toast.LENGTH_LONG).show()
    }


    protected fun applyState(state: State<List<Item>>) {
        when (state) {
            is Success -> itemAdapter.submitList(state.data) {
                progress.isVisible = false
            }
            is Fail -> {
                itemAdapter.submitList(emptyList()) {
                    progress.isVisible = false
                }

                Toast.makeText(applicationContext,"Loading Error: ${state.error}", Toast.LENGTH_SHORT).show()
            }
            is Pending -> progress.isVisible = true
        }
    }


    companion object {
        const val COL_COUNT = 2
    }
}