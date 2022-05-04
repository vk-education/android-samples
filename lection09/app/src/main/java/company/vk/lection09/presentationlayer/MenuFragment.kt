package company.vk.lection09.presentationlayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import company.vk.lection09.databinding.ContentMenuBinding

class MenuFragment: AbstractFragment<ContentMenuBinding>() {
	interface IListener {
		fun showWebView()
		fun showWebViewInteraction()
		fun filmList()
	}


	override fun createBinding(inflater: LayoutInflater, container: ViewGroup?) = ContentMenuBinding.inflate(inflater, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding().sampleWebview.setOnClickListener { router().showWebView() }
		binding().sampleWebviewInteraction.setOnClickListener { router().showWebViewInteraction() }
		binding().filmList.setOnClickListener { router().filmList() }
	}


	protected fun router() = listener<IListener>()


	companion object {
		const val TAG= "MenuFragment"


		fun newInstance(): MenuFragment {
			return MenuFragment()
		}
	}
}