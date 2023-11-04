package daniel.avila.rnm.kmm.presentation.ui.features.toolbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import daniel.avila.rnm.kmm.MR
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    title: String,
    onBackClick: () -> Unit
) {
    Row(
        modifier = modifier.wrapContentHeight().fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(MR.images.arrow_back),
            contentDescription = null,
            modifier = Modifier.size(25.dp).clickable(
                indication = null,
                interactionSource = MutableInteractionSource()
            ) {
                onBackClick()
            }
        )
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = title, modifier = Modifier.height(24.dp).weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.h4
        )
        Spacer(modifier = Modifier.width(15.dp))
        Icon(
            painter = painterResource(MR.images.menu),
            modifier = Modifier.width(24.dp).height(24.dp).clickable {

            },
            contentDescription = null
        )
    }
}