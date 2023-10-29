package daniel.avila.rnm.kmm.presentation.ui.features.profile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import daniel.avila.rnm.kmm.MR
import dev.icerock.moko.resources.ImageResource

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    val list = listOf(
        SettingItem(
            title = "Доступ к геопозиции",
            icon = MR.images.location,
            isSwitch = true,
            type = SettingType.LOCATION
        ),
        SettingItem(
            title = "Уведомления",
            icon = MR.images.notification,
            isSwitch = false,
            type = SettingType.NOTIFICATION
        ),
        SettingItem(
            title = "Избранные валюты",
            icon = MR.images.payments,
            isSwitch = false,
            type = SettingType.FAVORITE_CURRENCY
        ),
        SettingItem(
            title = "Выбор языка",
            icon = MR.images.translate,
            isSwitch = false,
            type = SettingType.LANGUAGE
        ),
    )
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(list) {
            SettingItemView(
                it
            )
        }
    }
}

data class SettingItem(
    val title: String,
    val icon: ImageResource,
    val isSwitch: Boolean,
    val type: SettingType
)

enum class SettingType {
    LOCATION,
    NOTIFICATION,
    FAVORITE_CURRENCY,
    LANGUAGE
}

