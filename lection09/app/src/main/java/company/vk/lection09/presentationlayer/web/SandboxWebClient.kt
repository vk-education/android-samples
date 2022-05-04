package company.vk.lection09.presentationlayer.web

import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import company.vk.base.loggers.Logger

class SandboxWebClient: WebViewClient() {
	override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
		super.onPageStarted(view, url, favicon)
	}

	override fun onPageFinished(view: WebView?, url: String?) {
		super.onPageFinished(view, url)
	}

	override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
		Logger.d("shouldOverrideUrlLoading($url)", "TECH")

		if (isForbiddenUrl(url)) {
			Toast.makeText(view!!.context, "Forbidden Url: $url", Toast.LENGTH_LONG).show()
			return true
		}

		return false
	}


	protected fun isForbiddenUrl(url: String?): Boolean {
		return !url.isNullOrBlank() && url.startsWith("http")
	}
}