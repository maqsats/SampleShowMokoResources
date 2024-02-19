import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.dnapayments.mp.App
import com.dnapayments.mp.di.initKoin

fun main() = application {
    initKoin {}
    Window(
        title = "DNAPaymentsKMP",
        state = rememberWindowState(width = 400.dp, height = 800.dp),
        onCloseRequest = ::exitApplication,
    ) { App() }
}