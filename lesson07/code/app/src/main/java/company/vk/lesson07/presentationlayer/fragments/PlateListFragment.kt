package company.vk.lesson07.presentationlayer.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import company.vk.lesson07.R
import company.vk.lesson07.businesslayer.PlateProviders
import company.vk.lesson07.presentationlayer.adapters.PlateAdapter
import company.vk.lesson07.presentationlayer.models.PlateViewModel

class PlateListFragment: AbstractFragment<PlateListFragment.ListViewHolder>(R.layout.content_plate_list) {
    protected val viewModel by viewModels<PlateViewModel>()

    protected val plateAdapter by lazy { PlateAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val provider = provider()
        holder?.add?.setOnClickListener { addPlate(provider) }
        holder?.clear?.setOnClickListener { clearPlates(provider) }

        holder?.recycler?.apply {
            layoutManager = GridLayoutManager(requireContext(), COL_COUNT, RecyclerView.VERTICAL, false)
            adapter = plateAdapter
        }

        loadPlates(provider)
    }

    override fun createHolder(view: View) = ListViewHolder(view)

    protected fun loadPlates(provider: PlateProviders) {
        viewModel.all(provider).handle(plateAdapter::submitList)
    }

    protected fun clearPlates(provider: PlateProviders) {
        viewModel.clearAll(provider).handle { loadPlates(provider) }
    }

    protected fun addPlate(provider: PlateProviders) {
        viewModel.addPlate(provider).handle { loadPlates(provider) }
    }

    override fun applyStub(pending: Boolean, message: String?) {
        val isNeedBlock = !pending
        holder?.add?.isEnabled = isNeedBlock
        holder?.clear?.isEnabled = isNeedBlock

        holder?.pending?.isVisible = pending
        holder?.message?.isVisible = false

        if (message != null) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    class ListViewHolder(view: View): ViewHolder(view) {
        val recycler by lazy { view.findViewById<RecyclerView>(R.id.recycler) }
        val add by lazy { view.findViewById<View>(R.id.add_plate) }
        val clear by lazy { view.findViewById<View>(R.id.clear) }
    }

    companion object {
        const val TAG = "PlateListFragment"

        protected const val COL_COUNT = 3

        fun newInstance(provider: PlateProviders): PlateListFragment {
            return PlateListFragment().apply {
                arguments = bundleOf(EXTRAS_PROVIDER to provider.name)
            }
        }
    }
}