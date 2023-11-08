package com.dna.payments.kmm.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
        skipPartiallyExpanded = true
    ),
    scrimColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.30f),
    sheetShape: Shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp),
    sheetElevation: Dp = 30.dp,
    sheetBackgroundColor: Color = Color.Transparent,
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
        dragHandle = {
            Column {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                )
                Surface(
                    modifier = modifier
                        .fillMaxWidth().height(20.dp),
                    shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
                    shadowElevation = 20.dp
                ) {
                    Box(modifier = Modifier.background(Color.White))
                }
                Box(
                    modifier = modifier
                        .fillMaxWidth().wrapContentHeight().background(Color.White)
                        .padding(bottom = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier.height(5.dp)
                            .width(100.dp)
                            .background(
                                MaterialTheme.colors.secondary,
                                shape = RoundedCornerShape(3.dp)
                            )
                    )
                }
            }
        }
    )
}