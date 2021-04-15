package ru.hse.lection05.presentationlayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import ru.hse.lection05.R
import ru.hse.lection05.presentationlayer.Fail
import ru.hse.lection05.presentationlayer.Pending
import ru.hse.lection05.presentationlayer.Success
import ru.hse.lection05.presentationlayer.models.PlaceViewModel
import ru.hse.lection05.presentationlayer.adapters.PlacesAdapter

class PlaceListFragment: AbstractFragment() {
    protected val viewModel by activityViewModels<PlaceViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.contetn_places, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.fab).setOnClickListener {
            navigator().findPlaceScreen()
        }

        val placeAdapter = PlacesAdapter(this)
        view.findViewById<ViewPager2>(R.id.recycler).apply {
            adapter = placeAdapter
        }

        val stub = view.findViewById<TextView>(R.id.stub)
        val progress = view.findViewById<View>(R.id.progress)
        viewModel.places().observe(viewLifecycleOwner) {
            progress.isVisible = it is Pending

            when(it) {
                is Success -> {
                    placeAdapter.submitList(it.result)

                    if (it.result.isEmpty()) {
                        stub.text = "No local places"
                    } else {
                        stub.text = null
                    }
                }
                is Fail -> stub.text = "error: ${it.error}"
            }
        }
    }
}





