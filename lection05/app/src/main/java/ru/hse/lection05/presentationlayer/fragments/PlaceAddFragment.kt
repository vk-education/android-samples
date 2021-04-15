package ru.hse.lection05.presentationlayer.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.hse.lection05.R
import ru.hse.lection05.presentationlayer.Fail
import ru.hse.lection05.presentationlayer.Pending
import ru.hse.lection05.presentationlayer.Success
import ru.hse.lection05.presentationlayer.models.PlaceViewModel
import ru.hse.lection05.presentationlayer.models.SearchViewModel
import ru.hse.lection05.objects.Place
import ru.hse.lection05.presentationlayer.adapters.SuggestPlaceAdapter

class PlaceAddFragment: AbstractFragment(), SuggestPlaceAdapter.IListener {
    protected val viewModel by activityViewModels<PlaceViewModel>()
    protected val searchViewModel by viewModels<SearchViewModel>()

    val handler = Handler(Looper.getMainLooper())


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.content_place_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val suggestAdapter = SuggestPlaceAdapter(this)
        view.findViewById<RecyclerView>(R.id.recycler).apply {
            adapter = suggestAdapter
            layoutManager = LinearLayoutManager(context)
        }

        view.findViewById<EditText>(R.id.query).apply {
            addTextChangedListener(afterTextChanged = ::onQueryChanged)
        }

        val message = view.findViewById<TextView>(R.id.message).apply {
            setOnClickListener { searchViewModel.retry() }
        }

        val progress = view.findViewById<View>(R.id.progress)
        searchViewModel.searchResult().observe(viewLifecycleOwner) {
            progress.isVisible = it is Pending

            message.text = (it as? Fail)?.error?.message
            message.isVisible = it is Fail

            when (it is Success) {
                true -> suggestAdapter.submitList(it.result)
                false -> suggestAdapter.submitList(emptyList())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onSuggestClicked(item: Place) {
        viewModel.save(item) { result, error ->
            when (result) {
                true -> navigator().pop()
                false -> Toast.makeText(requireContext(), error?.message?: "UNKNOWN", Toast.LENGTH_LONG).show()
            }
        }
    }


    protected fun onQueryChanged(text: Editable?) {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed( { searchViewModel.updateQuery(text.toString()) }, 600)
    }
}





