package com.dna.payments.kmm.utils.webview.web

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dna.payments.kmm.utils.webview.jsbridge.WebViewJsBridge

/**
 * Android WebView implementation.
 */
@Composable
actual fun ActualWebView(
    state: WebViewState,
    modifier: Modifier,
    captureBackPresses: Boolean,
    navigator: WebViewNavigator,
    webViewJsBridge: WebViewJsBridge?,
    onCreated: () -> Unit,
    onDispose: () -> Unit,
) {
    AccompanistWebView(
        state,
        modifier,
        captureBackPresses,
        navigator,
        webViewJsBridge,
        onCreated = { _ -> onCreated() },
        onDispose = { _ -> onDispose() },
    )
}
