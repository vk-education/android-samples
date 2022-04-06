package com.example.lecture06.presentation

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.lecture06.R
import com.example.lecture06.presentation.list.CountryCovidHistoryAdapter
import com.example.lecture06.presentation.model.CountryCovidHistoryUIState
import com.example.lecture06.sl.ServiceLocator
import kotlinx.coroutines.launch

class CountryHistoryFragment : Fragment(R.layout.fragment_country_covid_history) {

    private val viewModelCovid: CountryCovidHistoryViewModel by viewModels {
        CountryCovidHistoryViewModel.Factory(ServiceLocator.countriesInteractor)
    }

    private val countryCovidHistoryAdapter = CountryCovidHistoryAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModelCovid.loadData()

        val recyclerView = view.findViewById<RecyclerView>(R.id.data_recycler_view)
        recyclerView.adapter = countryCovidHistoryAdapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        view.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
            .setOnRefreshListener(viewModelCovid::loadData)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModelCovid.state
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect(::updateUI)
        }

    }

    private fun updateUI(state: CountryCovidHistoryUIState) {
        val errorTextView = requireView().findViewById<TextView>(R.id.error_text_view)
        val processBar = requireView().findViewById<ProgressBar>(R.id.progress_circular)
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.data_recycler_view)
        val refreshLayout =
            requireView().findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)

        when (state) {
            CountryCovidHistoryUIState.Error -> {
                recyclerView.visibility = View.GONE
                processBar.visibility = View.GONE
                errorTextView.visibility = View.VISIBLE
                refreshLayout.isRefreshing = false
            }
            CountryCovidHistoryUIState.Loading -> {
                recyclerView.visibility = View.GONE
                processBar.visibility = View.VISIBLE
                errorTextView.visibility = View.GONE
            }
            is CountryCovidHistoryUIState.Success -> {
                recyclerView.visibility = View.VISIBLE
                processBar.visibility = View.GONE
                errorTextView.visibility = View.GONE
                countryCovidHistoryAdapter.submitList(state.data)
                refreshLayout.isRefreshing = false
            }
        }


    }

}

