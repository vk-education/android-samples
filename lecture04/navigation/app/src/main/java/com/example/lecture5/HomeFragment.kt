package com.example.lecture5

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<Button>(R.id.go_to_counter_button).setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections
                    .actionFragmentHomeToFragmentCounter(-1)
            )
        }
    }

}