package daniel.avila.rnm.kmm.presentation.ui.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.seiko.imageloader.rememberImagePainter
import daniel.avila.rnm.kmm.domain.model.city.City
import daniel.avila.rnm.kmm.presentation.ui.common.CustomToolbar
import daniel.avila.rnm.kmm.presentation.ui.features.biometry_check.BiometryCheck
import daniel.avila.rnm.kmm.presentation.ui.features.calculator.Calculator
import daniel.avila.rnm.kmm.presentation.ui.features.exchange_places.ExchangePlaces
import daniel.avila.rnm.kmm.presentation.ui.features.home.bottom_nav.BottomBarNav
import daniel.avila.rnm.kmm.presentation.ui.features.home.bottom_nav.BottomBarRoute
import daniel.avila.rnm.kmm.presentation.ui.features.map_screen.MapScreen

class HomeScreen : Screen {

    @Composable
    override fun Content() {

        var bottomBarRoute by remember { mutableStateOf(BottomBarRoute.MAIN) }

        val cityState = remember { mutableStateOf<City?>(null) }

        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.background(Color.White)
        ) {
            CustomToolbar(bottomBarRoute = bottomBarRoute, cityState = cityState)
            when (bottomBarRoute) {
                BottomBarRoute.MAIN -> Calculator(modifier = Modifier.weight(1f), cityState.value)
                BottomBarRoute.EXCHANGE_PLACES -> ExchangePlaces(modifier = Modifier.weight(1f))
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
    Image(
        painter = rememberImagePainter("https://irecommend.ru/sites/default/files/product-images/418515/yC8EhmuUOWyscAJHztEjzA.png"),
        contentDescription = null,
        modifier = Modifier
            .width(300.dp)
            .height(300.dp)
    )
    Text(text = title, modifier.fillMaxWidth())
}

