package company.vk.lection09.presentationlayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import company.vk.lection09.BuildConfig

abstract class AbstractFragment<T : ViewBinding> : Fragment() {
	protected var binding: T? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = createBinding(inflater, container)
		return binding().root
	}

	override fun onDestroyView() {
		binding = null
		super.onDestroyView()
	}


	protected inline fun <reified T> listener(): T {
		if (parentFragment is T) {
			return parentFragment as T
		}

		return activity as T
	}

	protected fun binding(): T = binding!!

	abstract fun createBinding(inflater: LayoutInflater, container: ViewGroup?): T?
}