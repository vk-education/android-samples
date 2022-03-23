package com.example.lecture5

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

class CounterFragment : Fragment(R.layout.fragment_counter) {

    private val args by navArgs<CounterFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val countTextView = view.findViewById<TextView>(R.id.count_text_view)
        val addButton = view.findViewById<Button>(R.id.add_button)

        var currentValue = args.currentValue
        countTextView.text = currentValue.toString()
        addButton.setOnClickListener {
            currentValue++
            countTextView.text = currentValue.toString()
        }

    }
}