package daniel.avila.rnm.kmm.presentation.ui.features.calculator.custom_keyboard

import androidx.compose.ui.platform.ClipboardManager

object ClipboardHelper {
    fun getTextFromClipboard(clipboardManager: ClipboardManager): String? {
        return clipboardManager.getText()?.text
    }
}