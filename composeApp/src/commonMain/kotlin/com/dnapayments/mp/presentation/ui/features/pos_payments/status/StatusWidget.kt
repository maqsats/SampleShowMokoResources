package com.dnapayments.mp.presentation.ui.features.pos_payments.status

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dnapayments.mp.MR
import com.dnapayments.mp.domain.model.pos_payments.PosPaymentStatusV2
import com.dnapayments.mp.presentation.theme.DnaTextStyle
import com.dnapayments.mp.presentation.theme.Paddings
import com.dnapayments.mp.presentation.theme.white
import com.dnapayments.mp.presentation.ui.common.DNAText
import com.dnapayments.mp.utils.extension.noRippleClickable
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun StatusWidget(
    status: PosPaymentStatusV2
) {
    DNAText(
        modifier = Modifier.wrapContentWidth(),
        text = status.displayName,
        style = DnaTextStyle.Medium14
    )
}


@Composable
fun StatusBottomSheet(
    onItemChange: (PosPaymentStatusV2) -> Unit,
    statusList: List<PosPaymentStatusV2>,
    selectedStatus: PosPaymentStatusV2
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = white)
            .padding(Paddings.medium),
        verticalArrangement = Arrangement.Top
    ) {
        DNAText(
            text = stringResource(MR.strings.status),
            style = DnaTextStyle.SemiBold20
        )

        Spacer(modifier = Modifier.height(Paddings.medium))

        LazyColumn {
            items(statusList) { item ->
                StatusItem(
                    status = item,
                    isSelected = selectedStatus == item,
                    onItemClick = {
                        onItemChange(item)
                    })
            }
        }

        Spacer(modifier = Modifier.height(Paddings.medium))
    }
}

@Composable
fun StatusItem(status: PosPaymentStatusV2, isSelected: Boolean, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = Paddings.standard
            )
            .noRippleClickable {
                if (!isSelected) {
                    onItemClick()
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DNAText(
            modifier = Modifier.weight(1f),
            text = status.displayName,
            style = if (isSelected) DnaTextStyle.SemiBold16 else DnaTextStyle.Medium16Grey5,
        )
        if (isSelected)
            Icon(
                modifier = Modifier.padding(start = Paddings.medium),
                painter = painterResource(
                    MR.images.ic_success
                ),
                tint = Color.Unspecified,
                contentDescription = null,
            )
    }
}