package com.dnapayments.mp.presentation.ui.features.new_payment_link

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import com.dnapayments.mp.MR
import com.dnapayments.mp.data.model.stores.StoresItem
import com.dnapayments.mp.domain.model.main_screens.ScreenName
import com.dnapayments.mp.presentation.model.ResourceUiState
import com.dnapayments.mp.presentation.theme.Dimens
import com.dnapayments.mp.presentation.theme.DnaTextStyle
import com.dnapayments.mp.presentation.theme.Paddings
import com.dnapayments.mp.presentation.theme.grey3
import com.dnapayments.mp.presentation.theme.white
import com.dnapayments.mp.presentation.ui.common.DNAText
import com.dnapayments.mp.presentation.ui.common.DNAYellowButton
import com.dnapayments.mp.presentation.ui.common.DnaBottomSheet
import com.dnapayments.mp.presentation.ui.common.DnaTextField
import com.dnapayments.mp.presentation.ui.common.UiStateController
import com.dnapayments.mp.utils.date_picker.DnaDatePickerDialog
import com.dnapayments.mp.utils.extension.hideKeyboardOnOutsideClick
import com.dnapayments.mp.utils.extension.noRippleClickable
import com.dnapayments.mp.utils.navigation.drawer_navigation.DrawerScreen
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import korlibs.time.DateTime
import korlibs.time.DateTimeSpan
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewPaymentLinkScreen : DrawerScreen {

    override val isFilterEnabled: Boolean = false

    @Composable
    override fun Content() {
    }

    @Composable
    override fun DrawerContent(isToolbarCollapsed: Boolean) {
        val newPaymentViewModel = getScreenModel<NewPaymentLinkViewModel>()
        val state by newPaymentViewModel.uiState.collectAsState()
        val openStoresBottomSheet = rememberSaveable { mutableStateOf(false) }
        var showDatePicker by remember { mutableStateOf(false) }

        UiStateController(state.createNewLinkState)

        if (showDatePicker) {
            DnaDatePickerDialog(
                minDate = DateTime.now().minus(DateTimeSpan(days = 1)).date,
                singleDatePicker = true,
                showDatePicker = showDatePicker,
                onDismiss = { showDatePicker = false },
                onDateSelected = {
                    showDatePicker = false
                    newPaymentViewModel.setEvent(
                        NewPaymentLinkContract.Event.OnDateSelected(
                            it
                        )
                    )
                }
            )
        }

        ChooseStoreBottomSheet(
            storesList = when (val result = state.storeList) {
                is ResourceUiState.Success -> result.data
                else -> emptyList()
            },
            onChooseStore = { storesItem ->
                newPaymentViewModel.setEvent(
                    NewPaymentLinkContract.Event.OnStoreItemChanged(
                        storesItem
                    )
                )
            },
            openBottomSheet = openStoresBottomSheet,
            selectedStore = state.selectedStore
        )

        Column(modifier = Modifier.fillMaxSize().hideKeyboardOnOutsideClick()) {

            Spacer(modifier = Modifier.height(Paddings.large))

            CreatePaymentLinkForm(stringResource(MR.strings.store)) {
                DnaTextField(
                    modifier = Modifier
                        .fillMaxWidth().pointerInput(Unit) {
                            awaitEachGesture {
                                awaitFirstDown(pass = PointerEventPass.Initial)
                                val upEvent =
                                    waitForUpOrCancellation(pass = PointerEventPass.Initial)
                                if (upEvent != null) {
                                    openStoresBottomSheet.value = true
                                }
                            }
                        },
                    textState = state.store,
                    placeholder = stringResource(MR.strings.select_store),
                    enabled = false,
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            modifier = Modifier
                                .size(Dimens.iconSize),
                            painter = painterResource(MR.images.ic_arrow_down),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                )
            }

            CreatePaymentLinkForm(stringResource(MR.strings.order_number)) {
                DnaTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textState = state.orderNumber,
                    placeholder = stringResource(MR.strings.order_number),
                    trailingIcon = {
                        Icon(
                            modifier = Modifier
                                .size(Dimens.iconSize).noRippleClickable {
                                    newPaymentViewModel.setEvent(NewPaymentLinkContract.Event.OnGenerateNewRandomNumberClick)
                                },
                            painter = painterResource(MR.images.ic_re_create_link),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                )
            }

            CreatePaymentLinkForm(stringResource(MR.strings.amount)) {
                DnaTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textState = state.amount,
                    maxLength = 10,
                    placeholder = stringResource(MR.strings.empty_amount),
                    trailingIcon = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(Paddings.extraLarge)
                                    .width((1.5).dp)
                                    .background(grey3)
                            )
                            Spacer(modifier = Modifier.width(Paddings.standard12dp))
                            DNAText(
                                text = state.selectedCurrency.name,
                                style = DnaTextStyle.Medium16
                            )
                            Spacer(modifier = Modifier.width(Paddings.standard))
                            Icon(
                                modifier = Modifier
                                    .size(Dimens.iconSize),
                                painter = painterResource(MR.images.ic_arrow_down),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                            Spacer(modifier = Modifier.width(Paddings.small))
                        }
                    }
                )
            }

            CreatePaymentLinkForm(stringResource(MR.strings.customer_name)) {
                DnaTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textState = state.customerName,
                    placeholder = stringResource(MR.strings.name)
                )
            }

            CreatePaymentLinkForm(stringResource(MR.strings.description)) {
                DnaTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textState = state.description,
                    placeholder = stringResource(MR.strings.name_of_service_or_item)
                )
            }

            CreatePaymentLinkForm(stringResource(MR.strings.link_expiry)) {
                DnaTextField(
                    modifier = Modifier
                        .fillMaxWidth().pointerInput(Unit) {
                            awaitEachGesture {
                                awaitFirstDown(pass = PointerEventPass.Initial)
                                val upEvent =
                                    waitForUpOrCancellation(pass = PointerEventPass.Initial)
                                if (upEvent != null) {
                                    showDatePicker = true
                                }
                            }
                        },
                    enabled = false,
                    readOnly = true,
                    textState = state.expiredDate,
                    placeholder = stringResource(MR.strings.select_date)
                )
            }

            DNAYellowButton(
                modifier = Modifier.padding(
                    start = Paddings.medium,
                    end = Paddings.medium,
                    bottom = Paddings.large
                ),
                text = stringResource(MR.strings.create_link),
                onClick = {
                    newPaymentViewModel.setEvent(NewPaymentLinkContract.Event.OnCreateNewLinkClicked)
                },
                screenName = ScreenName.CREATE_LINK
            )
        }
    }

    @Composable
    override fun DrawerHeader() {
        Column {
            Spacer(modifier = Modifier.height(Paddings.large))
            DNAText(
                text = stringResource(MR.strings.new_payment_link),
                style = DnaTextStyle.Bold20,
                modifier = Modifier.padding(
                    horizontal = Paddings.medium,
                    vertical = Paddings.standard
                )
            )
        }
    }

    @Composable
    override fun DrawerFilter() {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChooseStoreBottomSheet(
    modifier: Modifier = Modifier,
    storesList: List<StoresItem>,
    selectedStore: StoresItem?,
    onChooseStore: (StoresItem) -> Unit,
    openBottomSheet: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
) {
    val scope = rememberCoroutineScope()
    if (openBottomSheet.value) {
        DnaBottomSheet(
            onDismissRequest = {
                openBottomSheet.value = false
            }
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = white)
                    .padding(Paddings.medium),
                verticalArrangement = Arrangement.spacedBy(Paddings.medium)
            ) {
                DNAText(
                    text = stringResource(MR.strings.store),
                    style = DnaTextStyle.SemiBold20
                )

                LazyColumn {
                    items(storesList, key = { it.id }) { storeItem ->
                        StoreWidget(
                            storesItem = storeItem,
                            isSelected = storeItem == selectedStore,
                            onChooseStore = {
                                scope.launch {
                                    onChooseStore(it)
                                    delay(100)
                                    openBottomSheet.value = false
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(Paddings.minimum))
            }
        }
    }
}

@Composable
private fun StoreWidget(
    modifier: Modifier = Modifier,
    storesItem: StoresItem,
    isSelected: Boolean,
    onChooseStore: (StoresItem) -> Unit
) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .padding(
                start = Paddings.medium,
                top = Paddings.small,
                bottom = Paddings.small
            ).noRippleClickable {
                if (!isSelected) {
                    onChooseStore(storesItem)
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DNAText(
            modifier = Modifier.weight(1f),
            text = storesItem.name,
            style = if (isSelected) DnaTextStyle.Medium16 else DnaTextStyle.WithAlpha16,
        )
        if (isSelected)
            androidx.compose.material3.Icon(
                modifier = Modifier.padding(horizontal = Paddings.medium),
                painter = painterResource(
                    MR.images.ic_success
                ),
                tint = Color.Unspecified,
                contentDescription = null,
            )
    }
}


@Composable
private fun CreatePaymentLinkForm(
    title: String,
    isCompulsory: Boolean = true,
    hint: String? = null,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.padding(
            start = Paddings.medium,
            end = Paddings.medium,
            bottom = Paddings.large
        )
    ) {
        Row {
            if (isCompulsory) {
                DNAText(
                    text = "* ",
                    style = DnaTextStyle.MediumRed16
                )
            }
            DNAText(
                text = title,
                style = DnaTextStyle.Medium16Grey5
            )
        }
        Spacer(
            modifier = Modifier.height(Paddings.extraSmall)
        )
        content()
    }
}