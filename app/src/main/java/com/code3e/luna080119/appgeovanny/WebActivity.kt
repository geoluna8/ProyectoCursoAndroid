package com.code3e.luna080119.appgeovanny

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        val url = "http://blog.sistronico.com"

        //mostrar html remoto
        webView.loadUrl(url)
        //para mostrar html local
        //webView.loadData("<h1>Hello World</h1>","text/html","utf-8")
        webView.settings.javaScriptEnabled = true

        //Para detectar eventos del WebView implementamos el Listener
        webView.webViewClient = object : WebViewClient(){
            //Notifica cuando la pagina web termina de cargar
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                progressBar.visibility = View.GONE
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                println("Url: " + url)
                return false
            }

        }
    }
}
