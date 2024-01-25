package com.dna.payments.kmm.presentation.ui.features.payment_methods_detail

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.payment_methods.PaymentMethod
import com.dna.payments.kmm.domain.model.payment_methods.domain.Domain
import com.dna.payments.kmm.domain.model.payment_methods.setting.DetailTerminalSetting
import com.dna.payments.kmm.domain.model.payment_methods.setting.TerminalSetting
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.state.ComponentRectangleLineLong
import com.dna.payments.kmm.presentation.state.ComponentRectangleLineShort
import com.dna.payments.kmm.presentation.state.ManagementResourceUiState
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.theme.greyColorAlpha
import com.dna.payments.kmm.presentation.theme.greyColorBackground
import com.dna.payments.kmm.presentation.theme.outlineGreenColor
import com.dna.payments.kmm.presentation.ui.common.DNAGreenBackButton
import com.dna.payments.kmm.presentation.ui.common.DNASwitch
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.presentation.ui.common.DNATextWithBackground
import com.dna.payments.kmm.presentation.ui.common.DNATextWithIcon
import com.dna.payments.kmm.presentation.ui.common.SuccessPopup
import com.dna.payments.kmm.presentation.ui.common.UiStateController
import com.dna.payments.kmm.presentation.ui.common.UnregisterDomainDialog
import com.dna.payments.kmm.presentation.ui.features.payment_methods_add_domain.first_step.AddDomainFirstStepScreen
import com.dna.payments.kmm.utils.UiText
import com.dna.payments.kmm.utils.extension.noRippleClickable
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.NavigatorResultString
import com.dna.payments.kmm.utils.navigation.currentOrThrow
import com.dna.payments.kmm.utils.navigation.getResult
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.collectLatest

class DetailPaymentMethodsScreen(
    private val paymentMethod: PaymentMethod
) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val detailPaymentMethodsViewModel = getScreenModel<DetailPaymentMethodsViewModel>()
        val state by detailPaymentMethodsViewModel.uiState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val showUnregisterDomainSuccess = remember { mutableStateOf(false) }
        LaunchedEffect(key1 = Unit) {
            detailPaymentMethodsViewModel.setEvent(
                DetailPaymentMethodsContract.Event.OnInit(
                    paymentMethodsType =
                    paymentMethod.type
                )
            )
        }

        UiStateController(state.domainUnregister)

        LaunchedEffect(key1 = Unit) {
            detailPaymentMethodsViewModel.effect.collectLatest { effect ->
                when (effect) {
                    DetailPaymentMethodsContract.Effect.OnUnregisterNewDomainSuccess -> {
                        showUnregisterDomainSuccess.value = true
                    }
                }
            }
        }

        val showRegisterDomainSuccess = remember { mutableStateOf(false) }

        val result = getResult().value

        LaunchedEffect(result) {
            if (result == null) return@LaunchedEffect
            if (result is NavigatorResultString) {
                showRegisterDomainSuccess.value = result.value
            }
        }

        SuccessPopup(
            UiText.StringResource(MR.strings.domain_added),
            showRegisterDomainSuccess
        )

        SuccessPopup(
            UiText.StringResource(MR.strings.domain_deleted),
            showUnregisterDomainSuccess
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            DNAGreenBackButton(
                text = stringResource(MR.strings.back),
                onClick = { navigator.pop() },
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            PaymentMethodsContent(
                modifier = Modifier.wrapContentHeight(),
                terminalSettings = state.terminalSettings,
                domainList = state.domainList,
                addNewDomainClicked = {
                    navigator.push(AddDomainFirstStepScreen(paymentMethod))
                },
                onUnregisterDialog = {
                    detailPaymentMethodsViewModel.setEvent(
                        DetailPaymentMethodsContract.Event.OnUnregisterDomainItem(
                            paymentMethodType = paymentMethod.type,
                            domain = it
                        )
                    )
                }
            )
        }
    }

    @Composable
    private fun PaymentMethodsContent(
        modifier: Modifier = Modifier,
        terminalSettings: ResourceUiState<List<TerminalSetting>>,
        domainList: ResourceUiState<List<Domain>>,
        addNewDomainClicked: () -> Unit,
        onUnregisterDialog: (Domain) -> Unit
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()).padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = modifier
                        .shadow(1.dp, shape = RoundedCornerShape(4.dp))
                        .border(
                            BorderStroke(width = 1.dp, color = greyColorBackground),
                            shape = RoundedCornerShape(4.dp)
                        ).height(48.dp)
                        .background(Color.White)
                        .width(48.dp).padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(paymentMethod.icon),
                        contentDescription = stringResource(paymentMethod.title),
                        tint = Color.Unspecified,
                        modifier = modifier.height(40.dp).width(40.dp)
                    )
                }
                DNAText(
                    modifier = modifier.padding(start = 16.dp),
                    style = DnaTextStyle.SemiBold20,
                    text = stringResource(paymentMethod.title)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            DNAText(
                style = DnaTextStyle.Normal14,
                text = stringResource(paymentMethod.description)
            )
            Spacer(modifier = Modifier.height(24.dp))
            DNAText(
                style = DnaTextStyle.WithAlpha16,
                text = stringResource(MR.strings.terminals)
            )
            ManagementResourceUiState(
                modifier = modifier,
                resourceUiState = terminalSettings,
                successView = {
                    Column {
                        it.forEach {
                            TerminalSettingItem(
                                terminalSetting = it,
                                onItemClicked = {}
                            )
                        }
                    }
                },
                loadingView = {
                    Column {
                        for (i in 1..3) {
                            TerminalSettingItemOnLoading()
                        }
                    }
                },
                onCheckAgain = {},
                onTryAgain = {},
            )
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DNAText(
                    style = DnaTextStyle.WithAlpha16,
                    text = stringResource(MR.strings.domains)
                )
                DNATextWithIcon(
                    style = DnaTextStyle.WithAlpha16,
                    text = stringResource(MR.strings.add_new),
                    icon = MR.images.ic_add,
                    iconSize = 24.dp,
                    textColor = outlineGreenColor,
                    modifier = modifier.noRippleClickable {
                        addNewDomainClicked()
                    }
                )
            }
            Spacer(modifier = Modifier.height(Paddings.medium))
            ManagementResourceUiState(
                modifier = modifier.padding(bottom = Paddings.medium),
                resourceUiState = domainList,
                successView = {
                    Column {
                        it.forEach {
                            DomainsItem(
                                domain = it,
                                onUnregisterDialog = { onUnregisterDialog(it) }
                            )
                        }
                    }
                },
                loadingView = {
                    Column {
                        for (i in 1..3) {
                            DomainsItemOnLoading()
                        }
                    }
                },
                onCheckAgain = {},
                onTryAgain = {},
            )
        }
    }


    @Composable
    private fun TerminalSettingItem(
        modifier: Modifier = Modifier,
        terminalSetting: TerminalSetting,
        onItemClicked: (PaymentMethod) -> Unit
    ) {
        var isExpanded by remember { mutableStateOf<Boolean?>(null) }
        var currentRotation by remember { mutableStateOf(0f) }
        val rotation = remember { androidx.compose.animation.core.Animatable(currentRotation) }

        Box(
            modifier = modifier.padding(top = 8.dp)
                .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                .background(Color.White, RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .wrapContentHeight()
                .animateContentSize()
                .noRippleClickable {
                    if (terminalSetting.detailTerminalSettingList.isNotEmpty()) {
                        isExpanded = isExpanded != true
                    }
                }
        ) {
            LaunchedEffect(isExpanded)
            {
                if (isExpanded == null) return@LaunchedEffect
                rotation.animateTo(
                    targetValue = currentRotation + 180f,
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = LinearOutSlowInEasing
                    )
                ) {
                    currentRotation = value
                }
            }
            Column(modifier = modifier.padding(16.dp)) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        DNAText(
                            text = terminalSetting.name
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            DNAText(
                                style = DnaTextStyle.Normal14,
                                text = when (terminalSetting.countTerminal) {
                                    NO_TERMINALS_TO_CONFIGURE -> {
                                        stringResource(MR.strings.no_terminals_to_configure)
                                    }

                                    else -> {
                                        stringResource(
                                            MR.strings.count_terminals,
                                            terminalSetting.countTerminal
                                        )
                                    }
                                }
                            )
                            if (terminalSetting.countTerminal != NO_TERMINALS_TO_CONFIGURE) {
                                DNATextWithBackground(
                                    modifier = modifier.padding(start = 8.dp),
                                    style = when {
                                        terminalSetting.activeTerminal > NO_TERMINALS_TO_CONFIGURE ->
                                            DnaTextStyle.BackgroundGreen12

                                        else -> DnaTextStyle.BackgroundGrey12
                                    },
                                    text = when {
                                        terminalSetting.countTerminal == terminalSetting.activeTerminal
                                                && terminalSetting.countTerminal != NO_TERMINALS_TO_CONFIGURE -> {
                                            stringResource(MR.strings.all_active)
                                        }

                                        terminalSetting.activeTerminal > NO_TERMINALS_TO_CONFIGURE -> {
                                            stringResource(
                                                MR.strings.number_active,
                                                terminalSetting.activeTerminal
                                            )
                                        }

                                        else -> {
                                            stringResource(MR.strings.no_active)
                                        }
                                    }
                                )
                            }
                        }
                    }
                    if (terminalSetting.countTerminal > 0) {
                        Icon(
                            painter = painterResource(MR.images.ic_dropdown_arrow),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.rotate(rotation.value)
                        )
                    }
                }
                if (isExpanded == true) {
                    Spacer(modifier = Modifier.height(Paddings.small))
                    Column {
                        terminalSetting.detailTerminalSettingList.forEachIndexed { index, item ->
                            DetailTerminalSettingItem(
                                detailTerminalSetting = item
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun TerminalSettingItemOnLoading(
        modifier: Modifier = Modifier
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
                Column {
                    ComponentRectangleLineLong()
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        ComponentRectangleLineShort()
                    }
                }
            }
        }
    }


    @Composable
    private fun DomainsItem(
        modifier: Modifier = Modifier,
        domain: Domain,
        onUnregisterDialog: (Domain) -> Unit
    ) {
        var showUnregisterDomain by remember { mutableStateOf(false) }

        if (showUnregisterDomain) {
            UnregisterDomainDialog(
                onDismiss = { showUnregisterDomain = false },
                onConfirm = {
                    showUnregisterDomain = false
                    onUnregisterDialog(domain)
                }
            )
        }

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
                    text = domain.name,
                    maxLines = 1,
                    style = DnaTextStyle.Normal14
                )
                Icon(
                    painter = painterResource(MR.images.ic_trash),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.noRippleClickable {
                        showUnregisterDomain = true
                    }
                )
            }
        }
    }

    @Composable
    private fun DomainsItemOnLoading(
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
    private fun DetailTerminalSettingItem(
        modifier: Modifier = Modifier,
        detailTerminalSetting: DetailTerminalSetting
    ) {
        Box(
            modifier = modifier.padding(top = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DNAText(
                    text = detailTerminalSetting.id,
                    maxLines = 1,
                    modifier = modifier.padding(end = Paddings.small).weight(1f)
                )
                DNASwitch()
            }
        }
    }


    companion object {
        const val NO_TERMINALS_TO_CONFIGURE = 0
    }
}