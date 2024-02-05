import UIKit
import SwiftUI
import ComposeApp
import GoogleMaps
import MapKit
import Firebase

@main
struct iosApp: App {
    init() {
        KoinKt.doInitKoin()
        FirebaseApp.configure()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

struct ContentView: View {
    var body: some View {
        ComposeView().ignoresSafeArea(.keyboard)
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
