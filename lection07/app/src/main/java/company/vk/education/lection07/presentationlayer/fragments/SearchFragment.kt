package company.vk.education.lection07.presentationlayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import company.vk.education.lection07.databinding.ContentSearchFragmentBinding
import company.vk.education.lection07.presentationlayer.models.PlaceViewModel
import androidx.fragment.app.viewModels
import company.vk.education.lection07.presentationlayer.State

class SearchFragment: Fragment() {
    protected val viewModel by viewModels<PlaceViewModel>()

    protected var _binding: ContentSearchFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = ContentSearchFragmentBinding.inflate(inflater)
        return requireBinding().root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireBinding().query.addTextChangedListener {
            val query = it.toString()
            viewModel.find(query)
        }

        viewModel.state().observe(viewLifecycleOwner) {
            when(it) {
                is State.Pending -> {
                    requireBinding().stub.isVisible = true
                    requireBinding().stub.text = "Pending..."
                }
                is State.Fail -> {
                    requireBinding().stub.isVisible = true
                    requireBinding().stub.text = "Fail: ${it.error}"
                }
                is State.Success -> {
                    requireBinding().stub.isVisible = true
                    requireBinding().stub.text = "Success: ${it.data.size}"
                }
            }
        }
    }


    protected fun requireBinding() = _binding!!
}