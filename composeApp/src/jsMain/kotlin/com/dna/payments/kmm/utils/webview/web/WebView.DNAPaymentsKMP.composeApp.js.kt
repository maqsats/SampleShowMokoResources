package com.dna.payments.kmm.utils.webview.web

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dna.payments.kmm.utils.webview.jsbridge.WebViewJsBridge

/**
 * Expect API of [WebView] that is implemented in the platform-specific modules.
 */
@Composable
actual fun ActualWebView(
    state: WebViewState,
    modifier: Modifier,
    captureBackPresses: Boolean,
    navigator: WebViewNavigator,
    webViewJsBridge: WebViewJsBridge?,
    onCreated: () -> Unit,
    onDispose: () -> Unit
) {
}