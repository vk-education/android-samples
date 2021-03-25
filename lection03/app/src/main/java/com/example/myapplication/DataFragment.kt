package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class DataFragment: Fragment() {
    var navigator: INavigation? = null

    var textView: TextView? = null
    var cachedView: View? = null

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
        // грязный хак
//        if (cachedView == null) {
//            cachedView = inflater.inflate(R.layout.activity_main, container, false)
//        }
//
//        return cachedView

        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView = view.findViewById(R.id.dummy)

        textView?.apply {
            text = arguments!!.getString("timestamp")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        textView = null
    }


    fun updateData(data: String) {
        textView?.apply {
            text = data
        }
    }


    companion object {
        fun create(timestamp: String): DataFragment {
            val extras = Bundle().apply {
                putString("timestamp", timestamp)
            }

            return DataFragment().apply {
                arguments = extras
            }
        }
    }
}