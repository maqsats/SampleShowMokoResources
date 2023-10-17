package daniel.avila.rnm.kmm.presentation.ui.features.main.custom_keyboard

import androidx.compose.ui.platform.ClipboardManager

object ClipboardHelper {
    fun getTextFromClipboard(clipboardManager: ClipboardManager): String? {
        return clipboardManager.getText()?.text
    }
}