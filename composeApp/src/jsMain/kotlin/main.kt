import com.dna.payments.kmm.App
import com.dna.payments.kmm.di.initKoin
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    initKoin {}
    onWasmReady {
        BrowserViewportWindow("Rick N Morty KMM") {
            App()
        }
    }
}
