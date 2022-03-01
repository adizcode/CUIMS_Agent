package com.github.adizcode.cuims_agent

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.github.adizcode.cuims_agent.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val (uid, pass) =
            intent.getParcelableExtra<UserCredentials>(USER_CREDS) ?: UserCredentials(
                "Default UID",
                "Default Password"
            )

        binding.webView.apply {
            settings.javaScriptEnabled = true
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true
            settings.displayZoomControls = false
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    if (!(view == null || url == null)) {
                        val javascript =
                            when {
                                url == CUIMS_URL -> "document.getElementById(\"txtUserId\").value=\"${uid}\";document.getElementById(\"btnNext\").click();"
                                url.contains(CUIMS_URL) -> "document.getElementById(\"txtLoginPassword\").value=\"${pass}\";document.getElementById(\"btnLogin\").click();"
                                else -> ""
                            }
                        view.loadUrl("javascript:" + javascript + "void(0);")
                    }
                }
            }
            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            loadUrl(CUIMS_URL)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.webView.canGoBack()) {
            binding.webView.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event)
    }
}