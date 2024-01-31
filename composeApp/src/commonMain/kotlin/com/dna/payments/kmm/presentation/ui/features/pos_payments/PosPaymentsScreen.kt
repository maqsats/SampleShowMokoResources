package com.dna.payments.kmm.presentation.ui.features.pos_payments

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
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
import com.dna.payments.kmm.domain.model.online_payments.OnlinePaymentMethod.KLARNA
import com.dna.payments.kmm.domain.model.online_payments.OnlinePaymentMethod.OPEN_BANK
import com.dna.payments.kmm.domain.model.online_payments.OnlinePaymentMethod.PAY_BY_BANK
import com.dna.payments.kmm.domain.model.online_payments.OnlinePaymentMethod.UNDEFINED
import com.dna.payments.kmm.domain.model.pos_payments.PosPaymentCard
import com.dna.payments.kmm.domain.model.transactions.pos.PosTransaction
import com.dna.payments.kmm.presentation.state.ComponentCircle
import com.dna.payments.kmm.presentation.state.ComponentRectangleLineShort
import com.dna.payments.kmm.presentation.state.Empty
import com.dna.payments.kmm.presentation.state.PaginationUiStateManager
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.presentation.ui.common.DNATextWithIcon
import com.dna.payments.kmm.presentation.ui.common.DnaFilter
import com.dna.payments.kmm.presentation.ui.features.date_range.DateRangeBottomSheet
import com.dna.payments.kmm.presentation.ui.features.date_range.DateRangeWidget
import com.dna.payments.kmm.presentation.ui.features.pos_payments.status.StatusBottomSheet
import com.dna.payments.kmm.presentation.ui.features.pos_payments.status.StatusWidget
import com.dna.payments.kmm.presentation.ui.features.pos_payments_detail.DetailPosPaymentScreen
import com.dna.payments.kmm.utils.extension.changePlatformColor
import com.dna.payments.kmm.utils.extension.noRippleClickable
import com.dna.payments.kmm.utils.extension.toCurrencySymbol
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.currentOrThrow
import com.dna.payments.kmm.utils.navigation.drawer_navigation.DrawerScreen
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.collectLatest

class PosPaymentsScreen : DrawerScreen {
    override val key: ScreenKey = "PosPaymentsScreen"
    override val isFilterEnabled: Boolean = true

    @Composable
    override fun Content() {}

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun DrawerContent(isToolbarCollapsed: Boolean) {
        changePlatformColor()
        val posPaymentsViewModel = getScreenModel<PosPaymentsViewModel>()
        val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })
        val state by posPaymentsViewModel.uiState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = Unit) {
            posPaymentsViewModel.effect.collectLatest { effect ->
                when (effect) {
                    is PosPaymentsContract.Effect.OnPageChanged -> {
                        pagerState.animateScrollToPage(effect.position)
                    }
                }
            }
        }
        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                posPaymentsViewModel.setEvent(PosPaymentsContract.Event.OnPageChanged(page))
            }
        }

        PosPaymentsContent(
            modifier = Modifier.wrapContentHeight(),
            state = state,
            isToolbarCollapsed = isToolbarCollapsed,
            onRequestNextPage = {
                posPaymentsViewModel.setEvent(PosPaymentsContract.Event.OnLoadMore)
            },
            onClick = { navigator.push(DetailPosPaymentScreen(it)) },
            onRefresh = { posPaymentsViewModel.setEvent(PosPaymentsContract.Event.OnRefresh) }
        )
    }

    @Composable
    private fun PosPaymentsContent(
        modifier: Modifier = Modifier,
        state: PosPaymentsContract.State,
        isToolbarCollapsed: Boolean,
        onRequestNextPage: () -> Unit = {},
        onRefresh: () -> Unit = {},
        onClick: (posTransaction: PosTransaction) -> Unit
    ) {
        Column(
            modifier = modifier.padding(horizontal = Paddings.medium),
            verticalArrangement = Arrangement.Top
        ) {
            PaginationUiStateManager(
                modifier = modifier.fillMaxSize(),
                resourceUiState = state.pagingUiState,
                pagingList = state.posPaymentList,
                onRequestNextPage = onRequestNextPage,
                onRefresh = onRefresh,
                isToolbarCollapsed = isToolbarCollapsed,
                successItemView = { transaction ->
                    PosPaymentItem(
                        transaction = transaction,
                        onClick = onClick
                    )

                },
                loadingView = { PosPaymentOnLoading() },
                emptyView = { Empty(text = stringResource(MR.strings.no_payments_yet)) }
            )
        }
    }

    @Composable
    private fun PosPaymentItem(
        transaction: PosTransaction,
        modifier: Modifier = Modifier,
        onClick: (posTransaction: PosTransaction) -> Unit
    ) {
        val clipboardManager: ClipboardManager = LocalClipboardManager.current

        Box(
            modifier = modifier.padding(top = 2.dp, bottom = 6.dp)
                .shadow(2.dp, shape = RoundedCornerShape(8.dp))
                .background(Color.White, RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .wrapContentHeight()
                .noRippleClickable {
                    onClick(transaction)
                }
        ) {
            Column(modifier = modifier.padding(Paddings.medium)) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (transaction.transactionType.imageResource != null) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        transaction.transactionType.backgroundColor,
                                        CircleShape
                                    )
                                    .size(40.dp)
                            ) {
                                Image(
                                    painter = painterResource(transaction.transactionType.imageResource),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .padding(Paddings.small)
                                        .height(24.dp)
                                        .width(24.dp)
                                )
                            }
                        }
                        DNAText(
                            text = transaction.currency.toCurrencySymbol() + " " + transaction.amount.toString(),
                            style = DnaTextStyle.SemiBold20,
                            modifier = Modifier.padding(start = Paddings.small)
                        )
                    }
                    DNATextWithIcon(
                        text = transaction.status.displayName,
                        style = DnaTextStyle.WithAlphaNormal12,
                        icon = transaction.status.imageResource,
                        textColor = transaction.status.textColor,
                        backgroundColor = transaction.status.backgroundColor
                    )
                }
                Spacer(modifier = Modifier.height(Paddings.medium))
                Divider()
                Spacer(modifier = Modifier.height(Paddings.medium))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DNAText(
                        style = DnaTextStyle.WithAlpha14,
                        text = stringResource(MR.strings.payment_method)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        val type = PosPaymentCard.fromCardScheme(transaction.cardScheme)
                        val captureMethod = when (transaction.captureMethod) {
                            "Pay by Bank app" -> PAY_BY_BANK
                            "Open Banking" -> OPEN_BANK
                            "Klarna" -> KLARNA
                            else -> UNDEFINED
                        }
                        if (type != PosPaymentCard.UNDEFINED || captureMethod != UNDEFINED) {
                            type.imageResource?.let {
                                Icon(
                                    painter = painterResource(it),
                                    contentDescription = null,
                                    tint = Color.Unspecified,
                                    modifier = Modifier.height(24.dp).width(24.dp)
                                )
                            } ?: captureMethod.imageResource?.let {
                                Icon(
                                    painter = painterResource(it),
                                    contentDescription = null,
                                    tint = Color.Unspecified,
                                    modifier = Modifier.height(24.dp).width(24.dp)
                                )
                            }
                            DNAText(
                                modifier = modifier.padding(start = Paddings.small),
                                style = DnaTextStyle.Medium14,
                                text = transaction.cardMask.ifEmpty {
                                    stringResource(captureMethod.stringResource)
                                }
                            )
                        }
                    }

                }
                Spacer(modifier = Modifier.height(Paddings.medium))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DNAText(
                        style = DnaTextStyle.WithAlpha14,
                        text = stringResource(MR.strings.terminal_id)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier.noRippleClickable {
                            clipboardManager.setText(AnnotatedString(transaction.terminalId))
                        }
                    ) {
                        DNAText(
                            style = DnaTextStyle.Medium14,
                            text = transaction.terminalId
                        )
                        Icon(
                            painter = painterResource(MR.images.ic_copy),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.height(24.dp).width(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(Paddings.medium))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DNAText(
                        style = DnaTextStyle.WithAlpha14,
                        text = stringResource(MR.strings.transaction_id)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier.noRippleClickable {
                            clipboardManager.setText(AnnotatedString(transaction.transactionId))
                        }.padding(start = Paddings.medium)
                    ) {
                        DNAText(
                            style = DnaTextStyle.Medium14,
                            text = transaction.transactionId,
                            maxLines = 1
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
    override fun DrawerHeader() {  // Just for testing purposes
        Column {
            Spacer(modifier = Modifier.height(24.dp))
            DNAText(
                text = stringResource(MR.strings.pos_payments),
                style = DnaTextStyle.Bold20,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
            )
        }
    }

    @Composable
    override fun DrawerFilter() {
        val onlinePaymentsViewModel = getScreenModel<PosPaymentsViewModel>()
        val state by onlinePaymentsViewModel.uiState.collectAsState()
        val statusFilter = rememberSaveable { mutableStateOf(false) }
        val openDatePickerFilter = rememberSaveable { mutableStateOf(false) }

        LazyRow(modifier = Modifier.padding(start = Paddings.small)) {
            item {
                DnaFilter(
                    openBottomSheet = statusFilter,
                    dropDownContent = {
                        StatusWidget(state.selectedStatus)
                    },
                    bottomSheetContent = {
                        StatusBottomSheet(
                            statusList = state.statusList,
                            selectedStatus = state.selectedStatus,
                            onItemChange = {
                                statusFilter.value = false
                                onlinePaymentsViewModel.setEvent(
                                    PosPaymentsContract.Event.OnStatusChange(
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
                                onlinePaymentsViewModel.setEvent(
                                    PosPaymentsContract.Event.OnDateSelection(
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
    private fun PosPaymentOnLoading(
        modifier: Modifier = Modifier,
    ) {
        Box(
            modifier = modifier.padding(top = 2.dp, bottom = 6.dp)
                .shadow(2.dp, shape = RoundedCornerShape(8.dp))
                .background(Color.White, RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(modifier = modifier.padding(Paddings.medium)) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ComponentCircle(modifier = Modifier.size(40.dp))
                        ComponentRectangleLineShort(modifier = Modifier.padding(start = Paddings.small))
                    }
                    ComponentRectangleLineShort()
                }
                Spacer(modifier = Modifier.height(Paddings.medium))
                Divider()
                Spacer(modifier = Modifier.height(Paddings.medium))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ComponentRectangleLineShort()
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        ComponentRectangleLineShort(modifier = Modifier.padding(start = Paddings.small))
                    }

                }
                Spacer(modifier = Modifier.height(Paddings.medium))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ComponentRectangleLineShort()
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        ComponentRectangleLineShort(modifier = Modifier.padding(start = Paddings.small))
                    }
                }
                Spacer(modifier = Modifier.height(Paddings.medium))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ComponentRectangleLineShort()
                    ComponentRectangleLineShort(modifier = Modifier.padding(start = Paddings.small))
                }
                Spacer(modifier = Modifier.height(Paddings.small))
            }
        }
    }
}