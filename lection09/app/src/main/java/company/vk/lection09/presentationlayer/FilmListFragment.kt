package company.vk.lection09.presentationlayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import company.vk.base.films.objects.Film
import company.vk.base.videos.presentationlayer.FilmViewModel
import company.vk.base.videos.presentationlayer.States
import company.vk.lection09.databinding.ContentFilmListBinding
import company.vk.lection09.presentationlayer.adapters.FilmAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FilmListFragment: AbstractFragment<ContentFilmListBinding>(), FilmAdapter.IListener {
	protected val viewModel by viewModels<FilmViewModel>()


	override fun createBinding(inflater: LayoutInflater, container: ViewGroup?) = ContentFilmListBinding.inflate(inflater, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val filmAdapter = FilmAdapter(this)
		binding().recycler.apply {
			adapter = filmAdapter
			layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
		}

		viewLifecycleOwner.lifecycleScope.launch {
			viewModel.filmList().collectLatest {
				when(it) {
					is States.Success -> {
						filmAdapter.submitList(it.result)
						binding().recycler.isVisible = true
					}

					is States.Fail -> {
						Toast.makeText(requireContext(), it.error?.message?: "Unknown Error", Toast.LENGTH_LONG).show()
						binding().recycler.isVisible = false
					}

					else -> {
						binding().recycler.isVisible = false
					}
				}
			}
		}

		viewModel.load()
	}

	override fun onFilmClicked(view: View, item: Film) {

	}


	companion object {
		const val TAG = "FilmListFragment"


		fun newInstance(): FilmListFragment {
			return FilmListFragment()
		}
	}
}