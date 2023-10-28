package daniel.avila.rnm.kmm.presentation.ui.features.calculator.city

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import daniel.avila.rnm.kmm.domain.model.city.City

@Composable
fun CityItem(city: City, onClick: (City) -> Unit) {

    Box(modifier = Modifier.fillMaxWidth().wrapContentHeight().clickable(
        interactionSource = MutableInteractionSource(),
        indication = null
    ) { onClick(city) }) {
        Text(
            text = city.name,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                .padding(vertical = 10.dp, horizontal = 10.dp)
        )
    }
}
