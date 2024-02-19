import com.dnapayments.mp.App
import com.dnapayments.mp.di.initKoin
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    initKoin {}
    onWasmReady {
        BrowserViewportWindow("DNAPaymentsKMP") {
            App()
        }
    }
}
