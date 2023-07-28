package com.pusyuu.app

import android.net.Uri
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import android.content.pm.PackageManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myWebView: WebView = findViewById(R.id.webview)
        myWebView.settings.javaScriptEnabled = true
        myWebView.webViewClient = CustomWebViewClient()
        myWebView.loadUrl("https://21emon.wjg.jp/PusyuuApp")
    }

    inner class CustomWebViewClient : WebViewClient() {
        private fun isBrowserAppInstalled(intent: Intent): Boolean {
            val activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            return activities.isNotEmpty()
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (url != null && Uri.parse(url).host == "21emon.wjg.jp") {
                // 指定されたドメインの場合はWebView内で開く
                view?.loadUrl(url)
                return false
            } else {
                // 指定されたドメイン以外の場合はデフォルトブラウザを起動
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                if (isBrowserAppInstalled(intent)) {
                    startActivity(intent)
                } else {
                    // ブラウザがインストールされていない場合はトーストで知らせる
                    Toast.makeText(applicationContext, "ブラウザがインストールされていません", Toast.LENGTH_LONG).show()
                }
                return true
            }
        }
    }

    override fun onBackPressed() {
        val myWebView: WebView = findViewById(R.id.webview)
        if (myWebView.canGoBack()) {
            myWebView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun finish() {
    }
}