package com.example.ctrlabtest

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        webView = findViewById(R.id.webview)

        sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        val lastVisitedUrl = sharedPreferences.getString("last_visited_url", null)
        if (lastVisitedUrl != null) {
            webView.loadUrl(lastVisitedUrl)
        } else {
            webView.loadUrl("https://ohmytraff.space/api")
        }

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                saveLastVisitedUrl(url)
            }
        }
    }

    //Saving last visited URL in shared preferences
    private fun saveLastVisitedUrl(url: String?) {
        if (url != null) {
            val editor = sharedPreferences.edit()
            editor.putString("last_visited_url", url)
            editor.apply()
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.getOnBackPressedDispatcher().onBackPressed()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        //Saving last visited URL when activity closed
        saveLastVisitedUrl(webView.url)
    }

}