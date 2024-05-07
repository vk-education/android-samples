package com.vk.missingparts

import android.content.Context
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class WebViewDemoActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(WebAppInterface(applicationContext), "Android")
        webView.loadUrl("https://mail.ru/")

        val bgWebView = WebView(applicationContext)
        bgWebView.settings.javaScriptEnabled = true
        bgWebView.addJavascriptInterface(WebAppInterface(applicationContext), "Android")
        bgWebView.loadUrl("file:///android_asset/demo_page.html")

        webView.webViewClient = object : WebViewClient() {
            override fun shouldInterceptRequest(
                view: WebView?,
                webResourceRequest: WebResourceRequest?
            ): WebResourceResponse? {
                android.util.Log.w("taag", "url=${webResourceRequest?.url}")
                return super.shouldInterceptRequest(view, webResourceRequest)
            }
        }
    }

    private class WebAppInterface(private val context: Context) {
        @JavascriptInterface
        fun showToast(toast: String) {
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
        }
    }
}
