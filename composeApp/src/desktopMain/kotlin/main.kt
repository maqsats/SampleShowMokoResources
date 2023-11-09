import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.dna.payments.kmm.App
import com.dna.payments.kmm.di.initKoin

fun main() = application {
    initKoin {}
    Window(
        title = "DNAPaymentsKMP",
        state = rememberWindowState(width = 400.dp, height = 800.dp),
        onCloseRequest = ::exitApplication,
    ) { App() }
}