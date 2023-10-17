package daniel.avila.rnm.kmm.presentation.ui.common

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import daniel.avila.rnm.kmm.MR
import daniel.avila.rnm.kmm.presentation.ui.features.bottom_nav.BottomBarRoute
import dev.icerock.moko.resources.compose.painterResource


@Composable
fun CustomToolbar(modifier: Modifier = Modifier, bottomBarRoute: BottomBarRoute) {
    Row(
        modifier = modifier.fillMaxWidth().height(IntrinsicSize.Min)
            .padding(vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            modifier = Modifier.wrapContentWidth(),
            painter = painterResource(MR.images.tenge_today_logo),
            contentDescription = null,
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.weight(1f))
        if (bottomBarRoute == BottomBarRoute.EXCHANGE_PLACES) {
            Icon(
                modifier = Modifier.wrapContentWidth(),
                painter = painterResource(MR.images.search),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(19.dp))
        }
        Text(
            text = "Алматы",
            color = MaterialTheme.colors.primaryVariant,
            style = MaterialTheme.typography.h3
        )
        Spacer(modifier = Modifier.width(6.dp))
        Icon(
            modifier = Modifier.wrapContentWidth(),
            painter = painterResource(MR.images.location),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}