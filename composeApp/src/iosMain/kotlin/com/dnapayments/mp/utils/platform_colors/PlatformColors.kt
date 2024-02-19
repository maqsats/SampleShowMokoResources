package com.dnapayments.mp.utils.platform_colors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIApplication
import platform.UIKit.UIColor
import platform.UIKit.UINavigationBar
import platform.UIKit.UIScreen
import platform.UIKit.UIView
import platform.UIKit.UIWindow

@OptIn(ExperimentalForeignApi::class)
@Composable
private fun statusBarView() = remember {
    val keyWindow: UIWindow? =
        UIApplication.sharedApplication.windows.firstOrNull { (it as? UIWindow)?.isKeyWindow() == true } as? UIWindow

    val safeAreaInsets = UIApplication.sharedApplication.keyWindow?.safeAreaInsets

    val width = UIScreen.mainScreen.bounds.useContents { this.size.width }

    var topInset = 0.0

    safeAreaInsets?.let {
        topInset = safeAreaInsets.useContents {
            this.top
        }
    }

    val tag = 3848245L

    val statusBarMine = UIView(frame = CGRectMake(.0, .0, width, topInset))

    keyWindow?.viewWithTag(tag) ?: run {
        statusBarMine.tag = tag
        statusBarMine.layer.zPosition = 999999.0
        keyWindow?.addSubview(statusBarMine)
        statusBarMine
    }
}


@Composable
actual fun PlatformColors(
    statusBarColor: Color,
    navBarColor: Color
) {
    val statusBar = statusBarView()
    SideEffect {
        statusBar.backgroundColor = statusBarColor.toUIColor()
        UINavigationBar.appearance().backgroundColor = navBarColor.toUIColor()
    }
}

private fun Color.toUIColor(): UIColor = UIColor(
    red = this.red.toDouble(),
    green = this.green.toDouble(),
    blue = this.blue.toDouble(),
    alpha = this.alpha.toDouble()
)