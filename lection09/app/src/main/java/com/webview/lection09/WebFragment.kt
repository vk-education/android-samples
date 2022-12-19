package com.webview.lection09

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

const val assetUrl = "file:///android_asset/callback_interface_page.html"
const val url = "https://vk.legal"

val COLORS = arrayOf("red", "#00FF00", "#0000FF", "#FFFFFF")

class WebFragment : Fragment() {

    private lateinit var webView: WebView
    private lateinit var button: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        webView = view.findViewById(R.id.web_view)
        button = view.findViewById(R.id.button)

        webView.addJavascriptInterface(WebInterface(requireContext()), "AndroidInterface")
        webView.settings.apply {
            javaScriptEnabled = true
        }

        webView.loadUrl(assetUrl)


        button.setOnClickListener {
            val color = COLORS.random()
            webView.evaluateJavascript("changeBackgroundColor('$color')", null)
        }
    }
}