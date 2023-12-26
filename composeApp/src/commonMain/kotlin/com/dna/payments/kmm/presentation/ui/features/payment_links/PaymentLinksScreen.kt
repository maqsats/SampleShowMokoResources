package com.dna.payments.kmm.presentation.ui.features.payment_links

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.payment_links.PaymentLinkHeader
import com.dna.payments.kmm.domain.model.payment_links.PaymentLinkItem
import com.dna.payments.kmm.presentation.state.ComponentRectangleLineLong
import com.dna.payments.kmm.presentation.state.ManagementResourceUiState
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.presentation.ui.common.DNATextWithIcon
import com.dna.payments.kmm.presentation.ui.common.DnaFilter
import com.dna.payments.kmm.presentation.ui.features.date_range.DateRangeBottomSheet
import com.dna.payments.kmm.presentation.ui.features.date_range.DateRangeWidget
import com.dna.payments.kmm.presentation.ui.features.payment_links.status.StatusBottomSheet
import com.dna.payments.kmm.presentation.ui.features.payment_links.status.StatusWidget
import com.dna.payments.kmm.utils.extension.noRippleClickable
import com.dna.payments.kmm.utils.extension.toCurrencySymbol
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.currentOrThrow
import com.dna.payments.kmm.utils.navigation.drawer_navigation.DrawerScreen
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.collectLatest

class PaymentLinksScreen : DrawerScreen {
    override val key: ScreenKey = "PaymentLinksScreen"
    override val isFilterEnabled: Boolean = true

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val paymentLinksViewModel = getScreenModel<PaymentLinksViewModel>()
        val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })
        val state by paymentLinksViewModel.uiState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = Unit) {
            paymentLinksViewModel.setEvent(
                PaymentLinksContract.Event.OnInit
            )
            paymentLinksViewModel.effect.collectLatest { effect ->
                when (effect) {
                    is PaymentLinksContract.Effect.OnPageChanged -> {
                        pagerState.animateScrollToPage(effect.position)
                    }
                }
            }
        }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                paymentLinksViewModel.setEvent(PaymentLinksContract.Event.OnPageChanged(page))
            }
        }

        PaymentLinksContent(
            modifier = Modifier.wrapContentHeight(),
            state = state
        )
    }

    @Composable
    private fun PaymentLinksContent(
        modifier: Modifier = Modifier,
        state: PaymentLinksContract.State
    ) {
        Column(
            modifier = modifier.padding(horizontal = Paddings.medium),
            verticalArrangement = Arrangement.Top
        ) {
            ManagementResourceUiState(
                resourceUiState = state.paymentLinkList,
                successView = { paymentLinkByPeriods ->
                    LazyColumn(modifier = modifier) {
                        items(paymentLinkByPeriods) { paymentLinkByPeriodItem ->
                            when (paymentLinkByPeriodItem) {
                                is PaymentLinkItem -> {
                                    PaymentLinkItem(paymentLinkItem = paymentLinkByPeriodItem)
                                }

                                is PaymentLinkHeader -> {
                                    PaymentLinkItemHeader(paymentLinkHeader = paymentLinkByPeriodItem)
                                }
                            }
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
        Column {
            Spacer(modifier = Modifier.height(24.dp))
            DNAText(
                text = stringResource(MR.strings.payment_links),
                style = DnaTextStyle.Bold20,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }

    @Composable
    override fun DrawerFilter() {
        val paymentLinksViewModel = getScreenModel<PaymentLinksViewModel>()
        val state by paymentLinksViewModel.uiState.collectAsState()
        val statusFilter = rememberSaveable { mutableStateOf(false) }
        val openDatePickerFilter = rememberSaveable { mutableStateOf(false) }

        LazyRow(modifier = Modifier.padding(start = Paddings.small)) {
            item {
                DnaFilter(
                    openBottomSheet = statusFilter,
                    dropDownContent = {
                        StatusWidget(state)
                    },
                    bottomSheetContent = {
                        StatusBottomSheet(
                            state = state,
                            onItemChange = {
                                statusFilter.value = false
                                paymentLinksViewModel.setEvent(
                                    PaymentLinksContract.Event.OnStatusChange(
                                        it
                                    )
                                )
                            }
                        )
                    }
                )
            }
            item {
                DnaFilter(
                    openBottomSheet = openDatePickerFilter,
                    dropDownContent = {
                        DateRangeWidget(
                            state.dateRange.first
                        )
                    },
                    bottomSheetContent = {
                        DateRangeBottomSheet(
                            dateSelection = state.dateRange.second,
                            onDatePeriodClick = {
                                openDatePickerFilter.value = false
                                paymentLinksViewModel.setEvent(
                                    PaymentLinksContract.Event.OnDateSelection(
                                        it
                                    )
                                )
                            }
                        )
                    }
                )
            }
        }
    }

    @Composable
    private fun PaymentLinkItemHeader(
        modifier: Modifier = Modifier,
        paymentLinkHeader: PaymentLinkHeader
    ) {
        Box(
            modifier = modifier.padding(top = Paddings.small)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                DNAText(
                    style = DnaTextStyle.WithAlpha14,
                    text = paymentLinkHeader.title.getText()
                )
            }
        }
    }

    @Composable
    private fun PaymentLinkItem(
        modifier: Modifier = Modifier,
        paymentLinkItem: PaymentLinkItem
    ) {
        val clipboardManager: ClipboardManager = LocalClipboardManager.current

        Box(
            modifier = modifier.padding(vertical = 8.dp)
                .shadow(2.dp, shape = RoundedCornerShape(8.dp))
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
                    DNAText(
                        text = paymentLinkItem.currency.toCurrencySymbol() + " " + paymentLinkItem.amount.toString(),
                        style = DnaTextStyle.SemiBold20
                    )
                    DNATextWithIcon(
                        text = stringResource(paymentLinkItem.status.stringResource),
                        style = DnaTextStyle.WithAlphaNormal12,
                        icon = paymentLinkItem.status.icon,
                        secondIcon = paymentLinkItem.status.iconEnd,
                        textColor = paymentLinkItem.status.textColor,
                        backgroundColor = paymentLinkItem.status.backgroundColor
                    )
                }
                Spacer(modifier = Modifier.height(Paddings.medium))
                Image(painter = painterResource(MR.images.divider), contentDescription = null)
                Spacer(modifier = Modifier.height(Paddings.medium))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DNAText(
                        style = DnaTextStyle.WithAlpha14,
                        text = stringResource(MR.strings.customer)
                    )
                    DNAText(
                        style = DnaTextStyle.Medium14,
                        text = paymentLinkItem.customerName
                    )
                }
                Spacer(modifier = Modifier.height(Paddings.medium))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DNAText(
                        style = DnaTextStyle.WithAlpha14,
                        text = stringResource(MR.strings.order_number)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier.noRippleClickable {
                            clipboardManager.setText(AnnotatedString(paymentLinkItem.invoiceId))
                        }
                    ) {
                        DNAText(
                            style = DnaTextStyle.Medium14,
                            text = paymentLinkItem.invoiceId
                        )
                        Icon(
                            painter = painterResource(MR.images.ic_copy),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.height(24.dp).width(24.dp)
                        )
                    }

                }
                Spacer(modifier = Modifier.height(Paddings.small))
            }
        }
    }

    @Composable
    private fun TeammateItemOnLoading(
        modifier: Modifier = Modifier,
    ) {
        Box(
            modifier = modifier.padding(top = 8.dp)
                .shadow(2.dp, shape = RoundedCornerShape(8.dp))
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
}