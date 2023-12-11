package com.dna.payments.kmm.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.presentation.theme.Dimens
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.theme.white
import com.dna.payments.kmm.utils.extension.noRippleClickable
import dev.icerock.moko.resources.compose.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DnaFilter(
    modifier: Modifier = Modifier,
    openBottomSheet: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    dropDownContent: @Composable () -> Unit,
    bottomSheetContent: @Composable (ColumnScope.() -> Unit)
) {

    if (openBottomSheet.value) {
        DnaBottomSheet(
            sheetContent = bottomSheetContent,
            onDismissRequest = {
                openBottomSheet.value = false
            }
        )
    }

    Box(
        modifier = modifier
            .wrapContentWidth()
            .padding(Paddings.small6dp)
            .shadow(Paddings.small6dp, RoundedCornerShape(Paddings.small6dp))
            .background(
                color = white,
                shape = RoundedCornerShape(Paddings.small)
            ).noRippleClickable {
                openBottomSheet.value = true
            },
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier.padding(vertical = Paddings.small),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Spacer(modifier = Modifier.width(Paddings.standard12dp))
            dropDownContent()
            Spacer(modifier = Modifier.width(Paddings.small))
            Icon(
                modifier = Modifier
                    .size(Dimens.iconSize),
                painter = painterResource(MR.images.ic_arrow_down),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(Paddings.standard12dp))
        }
    }
}