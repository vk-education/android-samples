package company.vk.myapplication.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import company.vk.myapplication.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment() {

	private val viewModel by viewModels<MainViewModel>()

	private val catAdapter = CatAdapter()


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return inflater.inflate(R.layout.fragment_main, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		view.findViewById<RecyclerView>(R.id.recycler).apply {
			layoutManager = GridLayoutManager(context, 2)
			adapter = catAdapter
		}

		val stub = view.findViewById<TextView>(R.id.stub)


		viewLifecycleOwner.lifecycleScope.launch {
			stub.isVisible = true
			stub.text = "Pending"
			stub.setOnClickListener(null)

			try {
				val list = withContext(Dispatchers.IO) { viewModel.getCats() }
				catAdapter.submitList(list)

				stub.isVisible = false
			} catch (error: Throwable) {
				stub.isVisible = true
				stub.text = "Error: ${error.message}"
				error.printStackTrace()
				stub.setOnClickListener {
					// retry
				}
			}
		}
	}


	companion object {
		fun newInstance() = MainFragment()
	}
}