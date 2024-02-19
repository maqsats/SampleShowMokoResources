package com.dnapayments.mp.presentation.ui.features.online_payments.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dnapayments.mp.domain.model.online_payments.OnlinePaymentOperationType
import com.dnapayments.mp.presentation.theme.DnaTextStyle
import com.dnapayments.mp.presentation.theme.Paddings
import com.dnapayments.mp.presentation.theme.white
import com.dnapayments.mp.presentation.ui.common.DNAText
import com.dnapayments.mp.utils.extension.noRippleClickable
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun OnlinePaymentOperationWidget(
    state: DetailOnlinePaymentContract.State
) {
    if (state.operationTypeList.isEmpty())
        return
}


@Composable
fun OnlinePaymentOperationBottomSheet(
    state: DetailOnlinePaymentContract.State,
    onItemChange: (OnlinePaymentOperationType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = white)
            .padding(Paddings.medium),
        verticalArrangement = Arrangement.Top
    ) {
        LazyColumn {
            items(state.operationTypeList) { item ->
                OnlinePaymentOperationItem(
                    status = item,
                    onItemClick = {
                        onItemChange(item)
                    })
            }
        }
        Spacer(modifier = Modifier.height(Paddings.medium))
    }
}

@Composable
fun OnlinePaymentOperationItem(
    status: OnlinePaymentOperationType,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = Paddings.standard
            )
            .noRippleClickable {
                onItemClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DNAText(
            modifier = Modifier.weight(1f),
            text = stringResource(status.stringResource),
            style = DnaTextStyle.Medium16,
        )
    }
}