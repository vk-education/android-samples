package company.vk.lection09.presentationlayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import company.vk.lection09.databinding.ContentWebviewInteractionBinding
import company.vk.lection09.presentationlayer.web.CustomWebClient
import company.vk.lection09.presentationlayer.web.CustomWebInterface
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WebViewInteractionFragment: AbstractFragment<ContentWebviewInteractionBinding>() {
	// main link - https://developer.android.com/guide/webapps/webview


	override fun createBinding(inflater: LayoutInflater, container: ViewGroup?) = ContentWebviewInteractionBinding.inflate(inflater, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding().changeBackgroundColor.setOnClickListener {
			val color = COLORS.random()
			binding().webview.evaluateJavascript("changeBackgroundColor('$color')", null)
		}

		binding().requestResult.setOnClickListener {
			viewLifecycleOwner.lifecycleScope.launch {
				val data = getGhibliFilms()
				Toast.makeText(requireContext(), data, Toast.LENGTH_LONG).show()
			}
		}

		binding().webview.apply {
			addJavascriptInterface(CustomWebInterface(requireActivity()), CustomWebInterface.TAG)
			webViewClient = CustomWebClient()
		}

		binding().webview.settings.apply {
			javaScriptEnabled = true
		}


		val assetUrl2 = "file:///android_asset/callback_interface_page.html"
		binding().webview.loadUrl(assetUrl2)

		requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, CustomBackCallback())
	}

	protected fun onBackCLicked(): Boolean {
		if (binding().webview.canGoBack()) {
			binding().webview.goBack()
			return true
		}

		return false
	}

	protected suspend fun getGhibliFilms() = suspendCoroutine<String?> { continuation ->
		binding().webview.evaluateJavascript("getGhibliFilms()", ) {
			continuation.resume(it)
		}
	}


	inner class CustomBackCallback: OnBackPressedCallback(true) {
		override fun handleOnBackPressed() {
			if (!onBackCLicked()) {
				isEnabled = false
				requireActivity().onBackPressedDispatcher.onBackPressed()
			}
		}
	}

	companion object {
		const val TAG = "SampleWebViewInteractionFragment"

		protected val COLORS = arrayOf("red", "#00FF00", "#0000FF", "#FFFFFF")


		fun newInstance(): WebViewInteractionFragment {
			return WebViewInteractionFragment()
		}
	}
}