package daniel.avila.rnm.kmm.presentation.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberAsyncImagePainter
import daniel.avila.rnm.kmm.MR
import daniel.avila.rnm.kmm.domain.model.Character
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun CharacterItem(
    character: Character,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(MR.images.ic_upload),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 10.dp, start = 10.dp, bottom = 10.dp)
                .width(110.dp)
                .height(110.dp)
        )
        Text(
            text = character.name,
            modifier = Modifier
                .fillMaxWidth(),
            fontFamily = fontFamilyResource(MR.fonts.Opensans.extrabold)
        )
    }
}

