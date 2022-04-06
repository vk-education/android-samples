package com.example.lecture06.presentation.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lecture06.R
import com.example.lecture06.domain.models.CountryCovidHistory


private val diffUtil = object : DiffUtil.ItemCallback<CountryCovidHistory>() {
    override fun areItemsTheSame(
        oldItem: CountryCovidHistory,
        newItem: CountryCovidHistory
    ): Boolean =
        oldItem.country == newItem.country && oldItem.history.case.day == newItem.history.case.day

    override fun areContentsTheSame(
        oldItem: CountryCovidHistory,
        newItem: CountryCovidHistory
    ): Boolean = oldItem == newItem

}

class CountryCovidHistoryAdapter :
    ListAdapter<CountryCovidHistory, CountryCovidHistoryAdapter.CountryCovidHistoryViewHolder>(
        diffUtil
    ) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryCovidHistoryViewHolder {
        return CountryCovidHistoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_coutry_covid_history, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CountryCovidHistoryViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class CountryCovidHistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val countryTextView: TextView = view.findViewById(R.id.country_text_view)
        private val casesTextView: TextView = view.findViewById(R.id.cases_text_view)

        fun onBind(item: CountryCovidHistory) {
            countryTextView.text = item.country.name
            val cases = item.history.case.cases
            casesTextView.text =
                itemView.context.getString(R.string.cases_format)
                    .format(cases.active, cases.critical, cases.new, cases.recovered, cases.total)

        }

    }
}