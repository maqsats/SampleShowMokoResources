package com.dna.payments.kmm.presentation.ui.features.team_management

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.team_management.Teammate
import com.dna.payments.kmm.domain.model.team_management.UserStatus
import com.dna.payments.kmm.presentation.state.ComponentRectangleLineLong
import com.dna.payments.kmm.presentation.state.ManagementResourceUiState
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.presentation.ui.common.DNATextWithIcon
import com.dna.payments.kmm.presentation.ui.common.DnaTabRow
import com.dna.payments.kmm.utils.extension.capitalizeFirstLetter
import com.dna.payments.kmm.utils.extension.noRippleClickable
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.currentOrThrow
import com.dna.payments.kmm.utils.navigation.drawer_navigation.DrawerScreen
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.collectLatest

class TeamManagementScreen : DrawerScreen {
    override val key: ScreenKey = "TeamManagementScreen"
    override val isFilterEnabled: Boolean = true

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val teamManagementViewModel = getScreenModel<TeamManagementViewModel>()
        val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })
        val state by teamManagementViewModel.uiState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = Unit) {
            teamManagementViewModel.setEvent(
                TeamManagementContract.Event.OnInit
            )
            teamManagementViewModel.effect.collectLatest { effect ->
                when (effect) {
                    is TeamManagementContract.Effect.OnPageChanged -> {
                        pagerState.animateScrollToPage(effect.position)
                    }
                }
            }
        }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                teamManagementViewModel.setEvent(TeamManagementContract.Event.OnPageChanged(page))
            }
        }

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            userScrollEnabled = false,
            pageContent = {
                when (it) {
                    0 -> {
                        TeamManagementContent(
                            modifier = Modifier.wrapContentHeight(),
                            state = state
                        )
                    }

                    1 -> {
                        InvitedTeamManagementContent(
                            modifier = Modifier.wrapContentHeight(),
                            state = state
                        )
                    }
                }
            }
        )
    }

    @Composable
    private fun InvitedTeamManagementContent(
        modifier: Modifier,
        state: TeamManagementContract.State
    ) {
        Column(
            modifier = modifier.padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.Top
        ) {
            ManagementResourceUiState(
                modifier = modifier.padding(bottom = Paddings.medium),
                resourceUiState = state.teammateListInvited,
                successView = {
                    LazyColumn {
                        items(it) {
                            InvitedTeammateItem(teammate = it)
                        }
                    }
                },
                loadingView = {
                    Column {
                        for (i in 1..12) {
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
    override fun DrawerHeader() {
        val teamManagementViewModel = getScreenModel<TeamManagementViewModel>()
        val state by teamManagementViewModel.uiState.collectAsState()

        Column {
            DnaTabRow(
                tabList = listOf(
                    stringResource(MR.strings.all),
                    stringResource(MR.strings.invited),
                ),
                selectedPagePosition = state.selectedPage,
                onTabClick = {
                    teamManagementViewModel.setEvent(TeamManagementContract.Event.OnPageChanged(it))
                }
            )
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
        Image(
            modifier = Modifier.fillMaxSize(),
            alignment = Alignment.CenterStart,
            painter = painterResource(MR.images.header),
            contentDescription = null
        )
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
            ManagementResourceUiState(
                modifier = modifier.padding(bottom = Paddings.medium),
                resourceUiState = state.teammateListAll,
                successView = {
                    LazyColumn {
                        items(it) {
                            TeammateItem(teammate = it)
                        }
                    }
                },
                loadingView = {
                    Column {
                        for (i in 1..12) {
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
                .noRippleClickable {

                }
        ) {
            Column(modifier = modifier.padding(16.dp)) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        DNAText(
                            text = teammate.firstName + " " + teammate.lastName,
                            style = DnaTextStyle.SemiBold16
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            DNAText(
                                style = DnaTextStyle.WithAlpha12,
                                text = teammate.roles[0].capitalizeFirstLetter()
                            )
                        }
                    }
                    DNATextWithIcon(
                        text = stringResource(teammate.status.displayName),
                        style = DnaTextStyle.WithAlphaNormal12,
                        icon = teammate.status.icon,
                        textColor = teammate.status.textColor,
                        backgroundColor = teammate.status.backgroundColor
                    )
                }
            }
        }
    }

    @Composable
    private fun InvitedTeammateItem(
        modifier: Modifier = Modifier,
        teammate: Teammate
    ) {
        Box(
            modifier = modifier.padding(top = 8.dp)
                .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                .background(Color.White, RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .wrapContentHeight()
                .noRippleClickable {

                }
        ) {
            Column(modifier = modifier.padding(16.dp)) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        DNAText(
                            text = teammate.firstName + " " + teammate.lastName,
                            style = DnaTextStyle.SemiBold16
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            DNAText(
                                style = DnaTextStyle.WithAlpha12,
                                text = teammate.roles[0].capitalizeFirstLetter()
                            )
                        }
                    }
                    DNATextWithIcon(
                        text = stringResource(UserStatus.INVITED.displayName),
                        style = DnaTextStyle.WithAlphaNormal12,
                        icon = UserStatus.INVITED.icon,
                        textColor = UserStatus.INVITED.textColor,
                        backgroundColor = UserStatus.INVITED.backgroundColor
                    )
                }
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
                ComponentRectangleLineLong(
                    isLoadingCompleted = false,
                    isLightModeActive = true
                )
            }
        }
    }
}