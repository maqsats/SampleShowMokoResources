package daniel.avila.rnm.kmm.presentation.ui.features.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import daniel.avila.rnm.kmm.MR
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun SettingItemView(settingItem: SettingItem) {
    var switchState by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(horizontal = 10.dp)) {

        Divider(color = MaterialTheme.colors.secondary, thickness = 1.dp)

        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                .padding(horizontal = 10.dp, vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(settingItem.icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.size(15.dp))

            Text(
                text = settingItem.title,
                style = MaterialTheme.typography.button,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.size(15.dp))

            when {
                settingItem.isSwitch -> {
                    Switch(
                        checked = switchState,
                        onCheckedChange = { switchState = !switchState },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colors.surface,
                            checkedTrackColor = MaterialTheme.colors.surface,
                            uncheckedThumbColor = Color.Gray,
                            uncheckedTrackColor = Color.Gray
                        ),
                        modifier = Modifier.size(40.dp)
                    )
                }
                else -> {
                    Image(
                        painter = painterResource(MR.images.arrow_forward),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
