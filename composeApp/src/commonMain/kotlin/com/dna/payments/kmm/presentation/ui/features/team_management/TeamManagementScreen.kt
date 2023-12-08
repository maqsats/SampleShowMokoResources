package com.dna.payments.kmm.presentation.ui.features.team_management

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.team_management.Teammate
import com.dna.payments.kmm.presentation.state.ComponentRectangleLineLong
import com.dna.payments.kmm.presentation.state.ManagementResourceUiState
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.currentOrThrow
import com.dna.payments.kmm.utils.navigation.drawer_navigation.DrawerScreen
import dev.icerock.moko.resources.compose.stringResource

class TeamManagementScreen : DrawerScreen {
    override val key: ScreenKey = uniqueScreenKey
    override val isFilterEnabled: Boolean = false

    @Composable
    override fun DrawerContent() {
        val teamManagementViewModel = getScreenModel<TeamManagementViewModel>()
        val state by teamManagementViewModel.uiState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = Unit) {
            teamManagementViewModel.setEvent(
                TeamManagementContract.Event.OnInit
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            TeamManagementContent(
                modifier = Modifier.wrapContentHeight(),
                state = state
            )
            Spacer(modifier = Modifier.weight(0.5f))
        }
    }

    @Composable
    override fun DrawerHeader() {
        Column {
            Spacer(modifier = Modifier.height(24.dp))
            DNAText(
                text = stringResource(MR.strings.team_management),
                style = DnaTextStyle.Bold20,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }

    @Composable
    override fun DrawerFilter() {

    }

    @Composable
    private fun TeamManagementContent(
        modifier: Modifier = Modifier,
        state: TeamManagementContract.State
    ) {
        Column(
            modifier = modifier.padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = modifier.height(24.dp))
            ManagementResourceUiState(
                modifier = modifier.padding(bottom = Paddings.medium),
                resourceUiState = state.teammateListAll,
                successView = {
                    Column {
                        it.forEach {
                            TeammateItem(teammate = it)
                        }
                    }
                },
                loadingView = {
                    Column {
                        for (i in 1..3) {
                            TeammateItemOnLoading()
                        }
                    }
                },
                onCheckAgain = {},
                onTryAgain = {},
            )
        }
    }

    @Composable
    private fun TeammateItem(
        modifier: Modifier = Modifier,
        teammate: Teammate
    ) {
        Box(
            modifier = modifier.padding(top = 8.dp)
                .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                .background(Color.White, RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row(
                modifier = modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DNAText(
                    text = teammate.firstName
                )
            }
        }
    }

    @Composable
    private fun TeammateItemOnLoading(
        modifier: Modifier = Modifier,
    ) {
        Box(
            modifier = modifier.padding(top = 8.dp)
                .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                .background(Color.White, RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row(
                modifier = modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ComponentRectangleLineLong(isLoadingCompleted = false, isLightModeActive = true)
            }
        }
    }
}