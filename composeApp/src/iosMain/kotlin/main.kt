import androidx.compose.ui.window.ComposeUIViewController
import com.dna.payments.kmm.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController { App() }
}
