package daniel.avila.rnm.kmm.domain.bottom_nav

import daniel.avila.rnm.kmm.presentation.ui.features.home.bottom_nav.BottomBarRoute
import dev.icerock.moko.resources.ImageResource

data class BottomNavData(
    val nameStringRes: String,
    val imageResource: ImageResource,
    val route: BottomBarRoute
)