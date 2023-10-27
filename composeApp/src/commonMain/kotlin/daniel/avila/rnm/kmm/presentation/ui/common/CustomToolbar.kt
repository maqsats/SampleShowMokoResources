package daniel.avila.rnm.kmm.presentation.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import daniel.avila.rnm.kmm.MR
import daniel.avila.rnm.kmm.domain.model.city.City
import daniel.avila.rnm.kmm.presentation.ui.features.bottom_nav.BottomBarRoute
import daniel.avila.rnm.kmm.presentation.ui.features.main.city.CityScreen
import dev.icerock.moko.resources.compose.painterResource


@Composable
fun CustomToolbar(
    modifier: Modifier = Modifier,
    bottomBarRoute: BottomBarRoute,
    cityState: MutableState<City?>
) {

    val dialogState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = dialogState,
        elevation = 0.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(20.dp),
        autoDismiss = false,
        onCloseRequest = {

        },
        content = {
            CityScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp),
                onCitySelected = {
                    cityState.value = it
                    dialogState.hide()
                }
            )
        }
    )

    LaunchedEffect(key1 = dialogState, cityState) {
        if (cityState.value == null)
            dialogState.show()
    }

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
        if (bottomBarRoute == BottomBarRoute.MAIN) {
            Icon(
                modifier = Modifier.wrapContentWidth(),
                painter = painterResource(MR.images.search),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(19.dp))
        }
        Text(
            text = cityState.value?.name.orEmpty(),
            modifier = Modifier.clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) { dialogState.show() },
            color = MaterialTheme.colors.primaryVariant,
            style = MaterialTheme.typography.h3
        )
        Spacer(modifier = Modifier.width(6.dp))
        Icon(
            modifier = Modifier.wrapContentWidth()
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) {
                    dialogState.show()
                },
            painter = painterResource(MR.images.location),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}