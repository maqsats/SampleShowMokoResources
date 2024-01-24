package com.dna.payments.kmm.presentation.ui.features.online_payments.status

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
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.online_payments.OnlinePaymentStatus
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.theme.white
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.presentation.ui.features.online_payments.OnlinePaymentsContract
import com.dna.payments.kmm.utils.extension.noRippleClickable
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun StatusWidget(
    state: OnlinePaymentsContract.State
) {
    if (state.statusList.isEmpty())
        return
    DNAText(
        modifier = Modifier.wrapContentWidth(),
        text = stringResource(state.statusList[state.indexOfSelectedStatus].stringResource),
        style = DnaTextStyle.Medium14
    )
}


@Composable
fun StatusBottomSheet(
    state: OnlinePaymentsContract.State,
    onItemChange: (Int) -> Unit
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
            items(state.statusList) { item ->
                StatusItem(
                    status = item,
                    isSelected = state.statusList[state.indexOfSelectedStatus] == item,
                    onItemClick = {
                        onItemChange(state.statusList.indexOf(item))
                    })
            }
        }

        Spacer(modifier = Modifier.height(Paddings.medium))
    }
}

@Composable
fun StatusItem(status: OnlinePaymentStatus, isSelected: Boolean, onItemClick: () -> Unit) {
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
            text = stringResource(status.stringResource),
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