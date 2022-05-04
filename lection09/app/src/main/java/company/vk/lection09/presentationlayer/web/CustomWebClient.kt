package company.vk.lection09.presentationlayer.web

import android.net.http.SslError
import android.util.Log
import android.webkit.*
import company.vk.base.loggers.Logger

class CustomWebClient: WebViewClient() {
	override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
		Logger.d("shouldOverrideUrlLoading($url)", "TECH")

		return false
	}

	override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
		super.onReceivedSslError(view, handler, error)
	}

	override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
		super.onReceivedHttpError(view, request, errorResponse)
	}

	override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
		super.onReceivedError(view, request, error)
	}
}