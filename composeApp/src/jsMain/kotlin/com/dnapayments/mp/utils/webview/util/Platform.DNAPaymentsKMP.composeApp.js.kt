package com.dnapayments.mp.utils.webview.util

import com.dnapayments.mp.utils.webview.util.Platform

/**
 * Get the current platform.
 */
internal actual fun getPlatform(): Platform {
    return Platform.Web
}