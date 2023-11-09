package com.example.lection02.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.lection02.R

class FirstFragment() : Fragment(R.layout.first_fragment) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lection02", "Lection02 onCreate fragment")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Lection02", "Lection02 onStart fragment")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Lection02", "Lection02 onResume fragment")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Lection02", "Lection02 onPause fragment")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Lection02", "Lection02 onStop fragment")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lection02", "Lection02 onDestroy fragment")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Lection02", "Lection02 onViewCreated fragment")
        view.findViewById<Button>(R.id.button).setOnClickListener {
            activity?.supportFragmentManager?.setFragmentResult("result_key", Bundle().apply {
                putString("key", "Пока!")
            })
            activity?.onBackPressed()
        }

    }
}