package com.dnapayments.mp.utils.webview.web

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dnapayments.mp.utils.webview.jsbridge.WebViewJsBridge
import com.dnapayments.mp.utils.webview.web.WebViewNavigator
import com.dnapayments.mp.utils.webview.web.WebViewState

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