package com.example.lecture5

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class SimpleFragment : Fragment(R.layout.fragment_simple) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        view.findViewById<TextView>(R.id.tag_text_view).text = tag
    }

    companion object {
        fun newInstance() = SimpleFragment()
    }
}