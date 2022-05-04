package company.vk.lection09.presentationlayer

import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import company.vk.lection09.databinding.ContentWebviewBinding
import company.vk.lection09.presentationlayer.web.CustomWebClient
import company.vk.lection09.presentationlayer.web.SandboxWebClient

class WebViewFragment: AbstractFragment<ContentWebviewBinding>() {
	// main link - https://developer.android.com/guide/webapps/webview


	override fun createBinding(inflater: LayoutInflater, container: ViewGroup?) = ContentWebviewBinding.inflate(inflater, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding().webview.settings.apply {
			javaScriptEnabled = true
		}

		// load text as page
//		val unencodedHtml = "<html><body><h1>'%23' is the percent code for ‘#‘<h1/></body></html>";
//		val encodedHtml = Base64.encodeToString(unencodedHtml.toByteArray(), Base64.NO_PADDING)
//		binding().webview.loadData(encodedHtml, "text/html", "base64")

		// load asset as page
//		val assetUrl = "file:///android_asset/legal_info_page.html"
//		// another way - https://developer.android.com/reference/androidx/webkit/WebViewAssetLoader
//		binding().webview.apply {
//			webViewClient = SandboxWebClient()
//			loadUrl(assetUrl)
//		}

		// load external page
		val externalUrl = "https://vk.legal"
		binding().webview.apply {
			webViewClient = CustomWebClient()
			loadUrl(externalUrl)
		}


		requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, CustomBackCallback())
	}

	protected fun onBackCLicked(): Boolean {
		if (binding().webview.canGoBack()) {
			binding().webview.goBack()
			return true
		}

		return false
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
		const val TAG = "SampleWebViewFragment"


		fun newInstance(): WebViewFragment {
			return WebViewFragment()
		}
	}
}