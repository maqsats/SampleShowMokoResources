import UIKit
import SwiftUI
import ComposeApp
import GoogleMaps
import MapKit

@main
struct iosApp: App {
    init() {
        KoinKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView().ignoresSafeArea(.keyboard)
        }
    }
}

struct ContentView: View {
    var body: some View {
        ComposeView()
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        let googleMapsApiKey = "AIzaSyDxAEDo7w2Fm7dR3DEIyHAU5B-BNOADaQw"
        GMSServices.provideAPIKey(googleMapsApiKey)
        GMSServices.setMetalRendererEnabled(true)

        return MainKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
