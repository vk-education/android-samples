package com.lesson.lifecycle.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.lesson.lifecycle.R

fun createFirstFragment(
    field: String
): FirstFragment {
    return FirstFragment().apply {
        arguments = Bundle().apply { putString("key", field) }
    }
}

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.first_fragment, null, false)
        view.findViewById<Button>(R.id.button_action).setOnClickListener {
            activity?.supportFragmentManager?.let {
                val transaction = it.beginTransaction()
                transaction.add(R.id.container, createFirstFragment("Hi"))
                transaction.add(R.id.container, createFirstFragment("Hi"))
                transaction.commit()
            }
        }
        Log.d("TEST", "arguments ${arguments?.getString("key").orEmpty()}")
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("TEST", "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TEST", "onCreate")
    }


    override fun onStart() {
        super.onStart()
        Log.d("TEST", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TEST", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TEST", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TEST", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TEST", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TEST", "onDestroy")
    }

}