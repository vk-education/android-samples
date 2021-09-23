package ru.mail.education.lesson02

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import ru.mail.education.lesson02.databinding.ContentListBinding

class ListFragment : Fragment(), ItemAdapter.IListener {
    protected var binding: ContentListBinding? = null

    protected val itemAdapter = ItemAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val tmp = ContentListBinding.inflate(inflater)
        binding = tmp
        return tmp.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recycler?.apply {
            adapter = itemAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }

        binding?.fab?.setOnClickListener {
            addItem()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }

    override fun onItemClicked(number: Int) {
        Toast.makeText(requireContext(), "position: $number", Toast.LENGTH_LONG).show()

        parentFragmentManager.beginTransaction()
            .replace(R.id.container, NumberFragment.newInstance(number), "$number")
            .addToBackStack(null)
            .commit()
    }

    protected fun addItem() {
        itemAdapter.increment()
        binding?.recycler?.scrollToPosition(itemAdapter.itemCount - 1)
    }
}
