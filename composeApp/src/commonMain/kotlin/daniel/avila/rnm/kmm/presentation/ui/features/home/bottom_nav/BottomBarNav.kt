package daniel.avila.rnm.kmm.presentation.ui.features.home.bottom_nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import daniel.avila.rnm.kmm.MR
import daniel.avila.rnm.kmm.domain.bottom_nav.BottomNavData
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun BottomBarNav(
    modifier: Modifier = Modifier,
    currentRoute: BottomBarRoute,
    onRouteChange: (bottomRoute: BottomBarRoute) -> Unit
) {
    val items = listOf(
        BottomNavData("Калькулятор", MR.images.calculate, BottomBarRoute.MAIN),
        BottomNavData("Все точки", MR.images.format_list_bulleted, BottomBarRoute.EXCHANGE_PLACES),
        BottomNavData("Новости", MR.images.full_coverage, BottomBarRoute.NEWS),
        BottomNavData("Профиль", MR.images.person, BottomBarRoute.PROFILE)
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 10.dp,
        modifier = modifier.padding(top = 1.dp)
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                unselectedContentColor = MaterialTheme.colors.primaryVariant,
                selectedContentColor = MaterialTheme.colors.primary,
                icon = {
                    Icon(
                        modifier = Modifier.padding(4.dp),
                        painter = painterResource(item.imageResource),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        modifier = Modifier.padding(top = 8.dp, end = 2.dp, start = 2.dp),
                        text = item.nameStringRes,
                        style = MaterialTheme.typography.subtitle2,
                        textAlign = TextAlign.Center,
                        softWrap = false
                    )
                },
                onClick = {
                    onRouteChange.invoke(item.route)
                })
        }
    }
}
