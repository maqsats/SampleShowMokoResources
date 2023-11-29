package com.dna.payments.kmm.presentation.ui.features.drawer_navigation

import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.data.model.profile.Merchant
import com.dna.payments.kmm.domain.model.nav_item.NavItem
import com.dna.payments.kmm.domain.model.nav_item.NavItemPosition
import com.dna.payments.kmm.presentation.state.ManagementResourceUiState
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.theme.Shapes
import com.dna.payments.kmm.presentation.theme.blueDrawer
import com.dna.payments.kmm.presentation.theme.greyFirst
import com.dna.payments.kmm.presentation.ui.common.DNAOutlinedGreenButton
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.presentation.ui.common.DNAYellowButton
import com.dna.payments.kmm.presentation.ui.common.UiStateController
import com.dna.payments.kmm.utils.extension.noRippleClickable
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.currentOrThrow
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.collectLatest

class DrawerView(
    val onNavItemClick: (NavItemPosition) -> Unit = {}
) : Screen {

    @Composable
    override fun Content() {

        val drawerViewModel = getScreenModel<DrawerViewModel>()

        val state by drawerViewModel.uiState.collectAsState()

        val parentNavigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = Unit) {
            drawerViewModel.effect.collectLatest { effect ->
                when (effect) {
                    DrawerViewContract.Effect.OnMerchantChange -> {
                        parentNavigator.replace(DrawerNavigationScreen())
                    }
                }
            }
        }

        UiStateController(state.merchantChange)

        Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
            ManagementResourceUiState(
                resourceUiState = state.merchants,
                modifier = Modifier.fillMaxWidth(),
                successView = {
                    MerchantViewHeader(
                        merchants = it,
                        onMerchantClick = { merchantId ->
                            drawerViewModel.setEvent(
                                DrawerViewContract.Event.OnMerchantChange(
                                    merchantId
                                )
                            )
                        }
                    )
                },
                onCheckAgain = {

                },
                onTryAgain = {

                },
                loadingView = {
                    MerchantViewHeader(
                        merchants = listOf(
                            Merchant(
                                name = "Loading...",
                                companyName = "Loading...",
                                isActive = false,
                                isDefault = false, merchantId = "", portalGuideViewedDate = ""
                            )
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(Paddings.medium))

            AddButton()

            Spacer(modifier = Modifier.height(Paddings.small))

            VirtualTerminalButton()

            Spacer(modifier = Modifier.height(Paddings.medium))

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(state.navItems.value) { navItem ->
                    DrawerItem(navItem, onNavItemClick)
                }
            }

            ProfileItem()
        }
    }
}

@Composable
fun VirtualTerminalButton() {
    DNAOutlinedGreenButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Paddings.medium,
            ),
        onClick = {

        },
        content = {
            Row(modifier = Modifier.wrapContentWidth().wrapContentHeight()) {
                Icon(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    painter = painterResource(MR.images.ic_virtual_terminal),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(Paddings.standard))
                DNAText(
                    text = stringResource(MR.strings.virtual_terminal),
                    style = DnaTextStyle.GreenMedium16
                )
            }
        }
    )
}

@Composable
fun AddButton() {
    DNAYellowButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Paddings.medium,
            ),
        onClick = {

        },
        content = {
            Row(modifier = Modifier.wrapContentWidth().wrapContentHeight()) {
                Icon(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    painter = painterResource(MR.images.ic_add),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(Paddings.standard))
                DNAText(
                    text = stringResource(MR.strings.add_link),
                    style = DnaTextStyle.Medium16
                )
            }
        }
    )
}

@Composable
private fun MerchantViewHeader(
    merchants: List<Merchant>,
    modifier: Modifier = Modifier,
    onMerchantClick: (Merchant) -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.wrapContentHeight().fillMaxWidth().padding(
            start = Paddings.medium,
            end = Paddings.medium,
            top = Paddings.standard,
            bottom = Paddings.large
        ),
    ) {
        Box(
            modifier = Modifier
                .size(48.dp).background(blueDrawer, shape = Shapes.medium),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(MR.images.ic_store),
                contentDescription = null
            )
        }
        Column(modifier = Modifier.weight(1f).wrapContentHeight().animateContentSize()) {
            Row(
                modifier = Modifier.fillMaxWidth().noRippleClickable {
                    isExpanded = !isExpanded
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(Paddings.medium))
                Column(modifier = Modifier.weight(1f)) {
                    DNAText(
                        text = merchants.first().name,
                        style = DnaTextStyle.SemiBold20
                    )
                    DNAText(
                        text = merchants.first().companyName,
                        style = DnaTextStyle.WithAlpha12
                    )
                }
                Icon(
                    modifier = Modifier.padding(horizontal = Paddings.medium),
                    painter = painterResource(
                        if (isExpanded) MR.images.ic_dropdown_arrow_expanded
                        else MR.images.ic_dropdown_arrow
                    ),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(Paddings.medium))
                Column {
                    merchants.forEachIndexed { index, merchant ->
                        MerchantExpandableItem(
                            modifier = Modifier.fillMaxWidth(),
                            merchant = merchant,
                            isFirst = index == 0,
                            onMerchantClick = onMerchantClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MerchantExpandableItem(
    modifier: Modifier = Modifier,
    merchant: Merchant,
    isFirst: Boolean,
    onMerchantClick: (Merchant) -> Unit
) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .padding(
                start = Paddings.medium,
                top = Paddings.small,
                bottom = Paddings.small
            ).noRippleClickable {
                if (!isFirst) {
                    onMerchantClick(merchant)
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DNAText(
            modifier = Modifier.weight(1f),
            text = merchant.name,
            style = if (isFirst) DnaTextStyle.Medium16 else DnaTextStyle.WithAlpha16,
        )
        if (isFirst)
            Icon(
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
private fun DrawerItem(
    navItem: NavItem,
    onNavItemClick: (NavItemPosition) -> Unit = {}
) {
    Row(
        modifier = Modifier.padding(
            horizontal = Paddings.extraLarge,
            vertical = Paddings.standard
        )
            .noRippleClickable { onNavItemClick(navItem.position) }) {
        Icon(
            painter = painterResource(navItem.imageDrawableId),
            contentDescription = null,
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(Paddings.medium))
        DNAText(text = stringResource(navItem.title), style = DnaTextStyle.Medium16)
    }
}

@Composable
private fun ProfileItem(onProfileClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(greyFirst)
            .padding(
                start = Paddings.extraLarge,
                end = Paddings.extraLarge,
                top = Paddings.large,
                bottom = Paddings.xxLarge
            )
            .noRippleClickable(onProfileClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(MR.images.ic_profile),
            contentDescription = null,
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(Paddings.medium))
        DNAText(text = stringResource(MR.strings.profile), style = DnaTextStyle.Medium16)
    }
}