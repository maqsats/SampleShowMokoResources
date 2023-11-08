package daniel.avila.rnm.kmm.presentation.ui.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import daniel.avila.rnm.kmm.domain.model.city.City
import daniel.avila.rnm.kmm.presentation.ui.common.CustomToolbar
import daniel.avila.rnm.kmm.presentation.ui.common.LocalSelectedCity
import daniel.avila.rnm.kmm.presentation.ui.features.all_places.ExchangePlaces
import daniel.avila.rnm.kmm.presentation.ui.features.calculator.Calculator
import daniel.avila.rnm.kmm.presentation.ui.features.home.bottom_nav.BottomBarNav
import daniel.avila.rnm.kmm.presentation.ui.features.home.bottom_nav.BottomBarRoute
import daniel.avila.rnm.kmm.presentation.ui.features.map_screen.MapScreen
import daniel.avila.rnm.kmm.presentation.ui.features.profile.ProfileScreen


class HomeScreen : Screen {

    @Composable
    override fun Content() {

        var bottomBarRoute by rememberSaveable { mutableStateOf(BottomBarRoute.MAIN) }

        val cityState = rememberSaveable { mutableStateOf<City?>(null) }

        CompositionLocalProvider(
            LocalSelectedCity provides cityState.value
        ) {
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.background(Color.White)
            ) {
                CustomToolbar(bottomBarRoute = bottomBarRoute, cityState = cityState).Content()
                when (bottomBarRoute) {
                    BottomBarRoute.MAIN -> Calculator(
                        modifier = Modifier.weight(1f)
                    ).Content()
                    BottomBarRoute.EXCHANGE_PLACES -> ExchangePlaces(
                        modifier = Modifier.weight(1f)
                    ).Content()
                    BottomBarRoute.NEWS -> {
                        MapScreen(modifier = Modifier.weight(1f))
                    }
                    BottomBarRoute.PROFILE -> ProfileScreen(modifier = Modifier.weight(1f))
                }
                BottomBarNav(
                    modifier = Modifier.wrapContentHeight().fillMaxWidth(),
                    currentRoute = bottomBarRoute
                ) {
                    if (it != bottomBarRoute) {
                        bottomBarRoute = it
                    }
                }
            }
        }
    }
}