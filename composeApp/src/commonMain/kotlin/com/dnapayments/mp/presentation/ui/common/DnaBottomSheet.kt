package com.dnapayments.mp.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.contentColorFor
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dnapayments.mp.presentation.theme.Paddings
import com.dnapayments.mp.presentation.theme.greyColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DnaBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    ),
    scrimColor: Color = BottomSheetDefaults.ScrimColor,
    sheetShape: Shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp),
    sheetElevation: Dp = Paddings.normal,
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
                        .height(Paddings.normal)
                )
                Column(
                    modifier = modifier
                        .fillMaxWidth().wrapContentHeight().shadow(
                            Paddings.normal, shape = RoundedCornerShape(
                                topStart = Paddings.small,
                                topEnd = Paddings.small
                            )
                        ).background(
                            Color.White, shape = RoundedCornerShape(
                                topStart = Paddings.small,
                                topEnd = Paddings.small
                            )
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(Paddings.extraSmall))
                    Box(
                        modifier = Modifier.height(Paddings.extraSmall)
                            .width(Paddings.extraLarge)
                            .background(
                                greyColor,
                                shape = RoundedCornerShape(Paddings.xxSmall)
                            )
                    )
                    Spacer(modifier = Modifier.height(Paddings.extraSmall))
                }
            }
        }
    )
}