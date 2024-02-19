package com.dnapayments.mp.presentation.ui.features.team_management.status

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dnapayments.mp.MR
import com.dnapayments.mp.presentation.state.ComponentRectangleLineShort
import com.dnapayments.mp.presentation.state.ManagementResourceUiState
import com.dnapayments.mp.presentation.theme.DnaTextStyle
import com.dnapayments.mp.presentation.theme.Paddings
import com.dnapayments.mp.presentation.theme.white
import com.dnapayments.mp.presentation.ui.common.DNAText
import com.dnapayments.mp.presentation.ui.features.team_management.TeamManagementContract
import com.dnapayments.mp.utils.extension.noRippleClickable
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun StatusWidget(
    state: TeamManagementContract.State
) {
    ManagementResourceUiState(
        resourceUiState = state.roleList,
        successView = { role ->
            if (role.isEmpty())
                return@ManagementResourceUiState
            DNAText(
                modifier = Modifier.wrapContentWidth(),
                text = role[state.indexOfSelectedRole],
                style = DnaTextStyle.Medium14
            )
        },
        loadingView = {
            ComponentRectangleLineShort(
                modifier = Modifier
                    .width(30.dp)
            )
        },
        onCheckAgain = {},
        onTryAgain = {},
    )
}


@Composable
fun StatusBottomSheet(
    state: TeamManagementContract.State,
    onItemChange: (Int) -> Unit
) {
    ManagementResourceUiState(
        resourceUiState = state.roleList,
        successView = { role ->
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

                role.forEach { item ->
                    StatusItem(
                        status = item,
                        isSelected = role[state.indexOfSelectedRole] == item,
                        onItemClick = {
                            onItemChange(role.indexOf(item))
                        })
                }

                Spacer(modifier = Modifier.height(Paddings.medium))
            }
        },
        loadingView = {
            LazyColumn {
                item {
                    DNAText(text = stringResource(MR.strings.all_statuses))
                }
                items(10) {
                    ComponentRectangleLineShort(
                        modifier = Modifier
                            .width(30.dp)
                    )
                }
            }
        },
        onCheckAgain = {},
        onTryAgain = {},
    )
}

@Composable
fun StatusItem(status: String, isSelected: Boolean, onItemClick: () -> Unit) {
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
            text = status,
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