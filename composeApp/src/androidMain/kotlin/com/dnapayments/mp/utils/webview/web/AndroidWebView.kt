package com.dnapayments.mp.utils.webview.web

import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.dnapayments.mp.utils.webview.jsbridge.JsMessage
import com.dnapayments.mp.utils.webview.jsbridge.WebViewJsBridge
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json

/**
 * Created By Kevin Zou On 2023/9/5
 */

/**
 * Android implementation of [IWebView]
 */
class AndroidWebView(
    private val webView: WebView,
    override val scope: CoroutineScope,
    override val webViewJsBridge: WebViewJsBridge?,
) : IWebView {
    init {
        initWebView()
    }

    override fun canGoBack() = webView.canGoBack()

    override fun canGoForward() = webView.canGoForward()

    override fun loadUrl(
        url: String,
        additionalHttpHeaders: Map<String, String>,
    ) {
        webView.loadUrl(url, additionalHttpHeaders)
    }

    override fun loadHtml(
        html: String?,
        baseUrl: String?,
        mimeType: String?,
        encoding: String?,
        historyUrl: String?,
    ) {
        if (html == null) return
        webView.loadDataWithBaseURL(baseUrl, html, mimeType, encoding, historyUrl)
    }

    override suspend fun loadHtmlFile(fileName: String) {
        webView.loadUrl("file:///android_asset/$fileName")
    }

    override fun postUrl(
        url: String,
        postData: ByteArray,
    ) {
        webView.postUrl(url, postData)
    }

    override fun goBack() {
        webView.goBack()
    }

    override fun goForward() {
        webView.goForward()
    }

    override fun reload() {
        webView.reload()
    }

    override fun stopLoading() {
        webView.stopLoading()
    }

    override fun evaluateJavaScript(
        script: String,
        callback: ((String) -> Unit)?,
    ) {
        val androidScript = "javascript:$script"
        webView.post {
            webView.evaluateJavascript(androidScript, callback)
        }
    }

    override fun injectJsBridge() {
        if (webViewJsBridge == null) return
        super.injectJsBridge()
        val callAndroid =
            """
            window.${webViewJsBridge.jsBridgeName}.postMessage = function (message) {
                    window.androidJsBridge.call(message)
                };
            """.trimIndent()
        evaluateJavaScript(callAndroid)
    }

    override fun initJsBridge(webViewJsBridge: WebViewJsBridge) {
        webView.addJavascriptInterface(this, "androidJsBridge")
    }

    @JavascriptInterface
    fun call(request: String) {
        val message = Json.decodeFromString<JsMessage>(request)
        webViewJsBridge?.dispatch(message)
    }

    @JavascriptInterface
    fun callAndroid(
        id: Int,
        method: String,
        params: String,
    ) {
        webViewJsBridge?.dispatch(JsMessage(id, method, params))
    }
}
