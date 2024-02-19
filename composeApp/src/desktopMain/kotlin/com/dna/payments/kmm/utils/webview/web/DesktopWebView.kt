package com.dna.payments.kmm.utils.webview.web

import com.dna.payments.kmm.utils.webview.jsbridge.JsMessage
import com.dna.payments.kmm.utils.webview.jsbridge.WebViewJsBridge
import dev.datlag.kcef.KCEFBrowser
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import org.cef.browser.CefBrowser
import org.cef.browser.CefFrame
import org.cef.browser.CefMessageRouter
import org.cef.callback.CefQueryCallback
import org.cef.handler.CefMessageRouterHandlerAdapter
import org.cef.network.CefPostData
import org.cef.network.CefPostDataElement
import org.cef.network.CefRequest

/**
 * Created By Kevin Zou On 2023/9/12
 */
class DesktopWebView(
    private val webView: KCEFBrowser,
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
        if (additionalHttpHeaders.isNotEmpty()) {
            val request =
                CefRequest.create().apply {
                    this.url = url
                    this.setHeaderMap(additionalHttpHeaders)
                }
            webView.loadRequest(request)
        } else {
            webView.loadURL(url)
        }
    }

    override fun loadHtml(
        html: String?,
        baseUrl: String?,
        mimeType: String?,
        encoding: String?,
        historyUrl: String?,
    ) {
        if (html != null) {
            webView.loadHtml(html, baseUrl ?: KCEFBrowser.BLANK_URI)
        }
    }

    override suspend fun loadHtmlFile(fileName: String) {
        // TODO
    }

    override fun postUrl(
        url: String,
        postData: ByteArray,
    ) {
        val request =
            CefRequest.create().apply {
                this.url = url
                this.postData =
                    CefPostData.create().apply {
                        this.addElement(
                            CefPostDataElement.create().apply {
                                this.setToBytes(postData.size, postData)
                            },
                        )
                    }
            }
        webView.loadRequest(request)
    }

    override fun goBack() = webView.goBack()

    override fun goForward() = webView.goForward()

    override fun reload() = webView.reload()

    override fun stopLoading() = webView.stopLoad()

    override fun evaluateJavaScript(
        script: String,
        callback: ((String) -> Unit)?,
    ) {
        webView.evaluateJavaScript(script) {
            if (it != null) {
                callback?.invoke(it)
            }
        }
    }

    override fun injectJsBridge() {
        if (webViewJsBridge == null) return
        super.injectJsBridge()
        val callDesktop =
            """
            window.${webViewJsBridge.jsBridgeName}.postMessage = function (message) {
                    window.cefQuery({request:message});
                };
            """.trimIndent()
        evaluateJavaScript(callDesktop)
    }

    override fun initJsBridge(webViewJsBridge: WebViewJsBridge) {

        val router = CefMessageRouter.create()
        val handler =
            object : CefMessageRouterHandlerAdapter() {
                override fun onQuery(
                    browser: CefBrowser?,
                    frame: CefFrame?,
                    queryId: Long,
                    request: String?,
                    persistent: Boolean,
                    callback: CefQueryCallback?,
                ): Boolean {
                    if (request == null) {
                        return super.onQuery(
                            browser,
                            frame,
                            queryId,
                            request,
                            persistent,
                            callback,
                        )
                    }
                    val message = Json.decodeFromString<JsMessage>(request)
                    webViewJsBridge.dispatch(message)
                    return true
                }
            }
        router.addHandler(handler, false)
        webView.client.addMessageRouter(router)
    }
}
