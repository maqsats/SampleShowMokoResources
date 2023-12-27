package com.dna.payments.kmm.presentation.ui.features.online_payments

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.dna.payments.kmm.domain.model.team_management.UserStatus
import com.dna.payments.kmm.domain.model.transactions.Transaction
import com.dna.payments.kmm.presentation.state.ComponentRectangleLineLong
import com.dna.payments.kmm.presentation.state.ManagementResourceUiState
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.presentation.ui.common.DNATextWithIcon
import com.dna.payments.kmm.utils.extension.noRippleClickable
import com.dna.payments.kmm.utils.extension.toCurrencySymbol
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.currentOrThrow
import com.dna.payments.kmm.utils.navigation.drawer_navigation.DrawerScreen
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.collectLatest

class OnlinePaymentsScreen : DrawerScreen {
    override val key: ScreenKey = "OnlinePaymentsScreen"
    override val isFilterEnabled: Boolean = true

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {  // Just for testing purposes
        val onlinePaymentsViewModel = getScreenModel<OnlinePaymentsViewModel>()
        val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })
        val state by onlinePaymentsViewModel.uiState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = Unit) {
            onlinePaymentsViewModel.setEvent(
                OnlinePaymentsContract.Event.OnInit
            )
            onlinePaymentsViewModel.effect.collectLatest { effect ->
                when (effect) {
                    is OnlinePaymentsContract.Effect.OnPageChanged -> {
                        pagerState.animateScrollToPage(effect.position)
                    }
                }
            }
        }
        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                onlinePaymentsViewModel.setEvent(OnlinePaymentsContract.Event.OnPageChanged(page))
            }
        }

        OnlinePaymentsContent(
            modifier = Modifier.wrapContentHeight(),
            state = state
        )
    }

    @Composable
    private fun OnlinePaymentsContent(
        modifier: Modifier = Modifier,
        state: OnlinePaymentsContract.State
    ) {
        Column(
            modifier = modifier.padding(horizontal = Paddings.medium),
            verticalArrangement = Arrangement.Top
        ) {
            ManagementResourceUiState(
                resourceUiState = state.onlinePaymentList,
                successView = { transactionList ->
                    LazyColumn(modifier = modifier) {
                        items(transactionList) { paymentLinkByPeriodItem ->
                            OnlinePaymentItem(
                                transaction = paymentLinkByPeriodItem,
                                onClick = {}
                            )
                        }
                    }
                },
                loadingView = {
                    Column {
                        for (i in 1..12) {
                            OnlinePaymentOnLoading()
                        }
                    }
                },
                onCheckAgain = {},
                onTryAgain = {},
            )
        }
    }

    @Composable
    private fun OnlinePaymentItem(
        transaction: Transaction,
        modifier: Modifier = Modifier,
        onClick: () -> Unit
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
                            text = transaction.status ?: "",
                            style = DnaTextStyle.SemiBold16
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            DNAText(
                                style = DnaTextStyle.WithAlpha12,
                                text = "${transaction.amount} ${transaction.currency.toCurrencySymbol()}"
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun OnlinePaymentOnLoading(
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
                ComponentRectangleLineLong()
            }
        }
    }

    @Composable
    override fun DrawerHeader() {  // Just for testing purposes
        Column {
            Spacer(modifier = Modifier.height(24.dp))
            DNAText(
                text = stringResource(MR.strings.online_payments),
                style = DnaTextStyle.Bold20,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
            )
        }
    }

    @Composable
    override fun DrawerFilter() {  // Just for testing purposes

    }
}