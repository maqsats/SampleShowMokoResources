package daniel.avila.rnm.kmm

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import daniel.avila.rnm.kmm.presentation.theme.TengeTodayTheme
import daniel.avila.rnm.kmm.presentation.ui.features.exchange_places.ExchangePlaces

@Composable
@Preview
fun MyComposablePreview() {
    TengeTodayTheme {
        ExchangePlaces()
    }
}