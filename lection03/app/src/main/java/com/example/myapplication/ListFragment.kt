package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ListFragment: Fragment() {
    var navigator: INavigation? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

//        parentFragment
        navigator = (requireActivity() as? INavigation)
    }

    override fun onDetach() {
        super.onDetach()

        navigator = null
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.dummy).setOnClickListener {
            navigator?.showData(System.currentTimeMillis().toString())
        }
    }
}