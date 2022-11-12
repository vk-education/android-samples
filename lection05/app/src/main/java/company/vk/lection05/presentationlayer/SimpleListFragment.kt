package company.vk.lection05.presentationlayer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import company.vk.lection05.R
import company.vk.lection05.businesslayer.AbstractItemProvider
import company.vk.lection05.businesslayer.ItemProviderFactor
import company.vk.lection05.objects.Strategies
import company.vk.lection05.presentationlayer.adapters.ItemAdapter

class SimpleListFragment : Fragment() {
	protected val provider by lazy { initializeProvider() }
	protected val itemAdapter = ItemAdapter()


	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.content_list, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		view.findViewById<RecyclerView>(R.id.list).apply {
			layoutManager = GridLayoutManager(requireContext(), COLUMN_COUNT)
			adapter = itemAdapter
		}

		provider.load {
			Log.d("TECH", "submit list with size=${it.size}")
			itemAdapter.submitList(it)
		}
	}

	protected fun strategy(): Strategies {
		val strategyName = requireArguments().getString(STRATEGY)!!
		return Strategies.valueOf(strategyName)
	}

	protected fun initializeProvider(): AbstractItemProvider {
		val strategy = strategy()
		val provider = ItemProviderFactor.create(strategy)

		return provider
	}


	companion object {
		const val TAG = "ListFragment"

		protected const val STRATEGY = "STRATEGY"
		protected const val COLUMN_COUNT = 3

		fun newInstance(strategy: Strategies): SimpleListFragment {
			val extras = Bundle().apply {
				putString(STRATEGY, strategy.name)
			}

			val fragment = SimpleListFragment().apply {
				arguments = extras
			}

			return fragment
		}
	}
}