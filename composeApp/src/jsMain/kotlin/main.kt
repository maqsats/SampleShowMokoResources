import com.dna.payments.kmm.App
import com.dna.payments.kmm.di.initKoin
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    initKoin {}
    onWasmReady {
        BrowserViewportWindow("DNAPaymentsKMP") {
            App()
        }
    }
}
