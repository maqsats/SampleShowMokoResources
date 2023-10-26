package daniel.avila.rnm.kmm.presentation.ui.features.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

@Composable
fun DialogScreen() {
    val dialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = dialogState, elevation = 0.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(30.dp),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Hello World")
            }
        }
    )
    LaunchedEffect(dialogState) {
        /* This should be called in an onClick or an Effect */
        dialogState.show()
    }
}