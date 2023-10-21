package daniel.avila.rnm.kmm.presentation.ui.features.main.exchange

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberAsyncImagePainter

@Composable
fun ExchangeItem(state: ExchangeItemState) {

    Row(
        modifier = Modifier.wrapContentHeight()
            .fillMaxWidth().padding(horizontal = 15.dp, vertical = 15.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://play-lh.googleusercontent.com/xujXQTNmLHTZUit5_qZTfUjw1tRK7wlEWYE-JXPokn6Eaoi7hXCL5O5QuQa4bbYgdL4"),
            contentDescription = null,
            modifier = Modifier
                .width(36.dp)
                .height(36.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
            Text(state.name, style = MaterialTheme.typography.button)

            Spacer(modifier = Modifier.height(3.dp))

            Text(state.howFar + " " + state.location, style = MaterialTheme.typography.h6)
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.wrapContentWidth(), horizontalAlignment = Alignment.End) {

            Text(state.priceTotal, style = MaterialTheme.typography.button)

            Spacer(modifier = Modifier.height(3.dp))

            Text(state.priceForOneDollar, style = MaterialTheme.typography.h6)
        }
    }
}

data class ExchangeItemState(
    val name: String,
    val priceTotal: String,
    val location: String,
    val howFar: String,
    val priceForOneDollar: String,
)

