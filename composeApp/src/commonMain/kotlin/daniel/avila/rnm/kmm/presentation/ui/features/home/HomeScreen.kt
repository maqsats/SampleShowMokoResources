package daniel.avila.rnm.kmm.presentation.ui.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import cafe.adriel.voyager.core.screen.Screen
import daniel.avila.rnm.kmm.domain.model.city.City
import daniel.avila.rnm.kmm.presentation.ui.common.CustomToolbar
import daniel.avila.rnm.kmm.presentation.ui.features.biometry_check.BiometryCheck
import daniel.avila.rnm.kmm.presentation.ui.features.bottom_nav.BottomBarNav
import daniel.avila.rnm.kmm.presentation.ui.features.bottom_nav.BottomBarRoute
import daniel.avila.rnm.kmm.presentation.ui.features.main.Calculator
import daniel.avila.rnm.kmm.presentation.ui.features.map_screen.MapScreen

class HomeScreen : Screen {

    @Composable
    override fun Content() {

        var bottomBarRoute by remember { mutableStateOf(BottomBarRoute.MAIN) }

        val focusManager = LocalFocusManager.current

        val cityState = remember { mutableStateOf<City?>(null) }

        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.background(Color.White).clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus()
            }
        ) {
            CustomToolbar(bottomBarRoute = bottomBarRoute, cityState = cityState)
            when (bottomBarRoute) {
                BottomBarRoute.MAIN -> Calculator(modifier = Modifier.weight(1f), cityState.value)
                BottomBarRoute.EXCHANGE_PLACES -> EmptyScreen(modifier = Modifier.weight(1f))
                BottomBarRoute.NEWS -> {
                    MapScreen(modifier = Modifier.weight(1f))
                }
                BottomBarRoute.PROFILE -> BiometryCheck(modifier = Modifier.weight(1f))
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

@Composable
fun EmptyScreen(modifier: Modifier, title: String = "EmptyScreen") {
    Text(text = title, modifier.fillMaxWidth())
}

