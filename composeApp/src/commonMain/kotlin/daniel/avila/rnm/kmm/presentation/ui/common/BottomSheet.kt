package daniel.avila.rnm.kmm.presentation.ui.common

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    ),
    scrimColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.30f),
    sheetShape: Shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    sheetElevation: Dp = 30.dp,
    sheetBackgroundColor: Color = Color.White,
    sheetContentColor: Color = contentColorFor(sheetBackgroundColor),
    onDismissRequest: () -> Unit = {},
    sheetContent: @Composable (ColumnScope.() -> Unit),
) {
    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        shape = sheetShape,
        scrimColor = scrimColor,
        tonalElevation = sheetElevation,
        containerColor = sheetBackgroundColor,
        contentColor = sheetContentColor,
        onDismissRequest = onDismissRequest,
        content = sheetContent,
        windowInsets = WindowInsets(0)
    )
}