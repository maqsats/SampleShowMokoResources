package com.dnapayments.mp.domain.interactors.use_cases.drawer

import com.dnapayments.mp.MR
import com.dnapayments.mp.domain.interactors.use_cases.access_level.AccessLevelUseCase
import com.dnapayments.mp.domain.model.nav_item.NavItem
import com.dnapayments.mp.domain.model.nav_item.NavItemPosition
import com.dnapayments.mp.domain.model.nav_item.SettingsItem
import com.dnapayments.mp.domain.model.nav_item.SettingsPosition
import com.dnapayments.mp.domain.model.permissions.AccessLevel
import com.dnapayments.mp.domain.model.permissions.Section
import org.koin.core.component.KoinComponent

class DrawerUseCase(private val accessLevelUseCase: AccessLevelUseCase) : KoinComponent {

    fun getNavItemList(): List<NavItem> = arrayListOf(
        NavItem(
            MR.images.ic_home,
            MR.strings.overview,
            NavItemPosition.OVERVIEW,
            isAvailable = accessLevelUseCase.hasPermission(
                Section.OVERVIEW,
                AccessLevel.READ
            )
        ),
        NavItem(
            MR.images.ic_pos_payments,
            MR.strings.pos_payments,
            NavItemPosition.POS_PAYMENTS,
            isAvailable = accessLevelUseCase.hasPermission(
                Section.POS_PAYMENTS,
                AccessLevel.FULL,
                AccessLevel.READ
            )
        ),
        NavItem(
            MR.images.ic_website_payments,
            MR.strings.online_payments,
            NavItemPosition.ONLINE_PAYMENTS,
            isAvailable = accessLevelUseCase.hasPermission(
                Section.ONLINE_PAYMENTS,
                AccessLevel.FULL,
                AccessLevel.READ
            )
        ),
        NavItem(
            MR.images.ic_payment_links,
            MR.strings.payment_links,
            NavItemPosition.PAYMENT_LINKS,
            isAvailable = accessLevelUseCase.hasPermission(
                Section.PAYMENT_LINKS,
                AccessLevel.READ,
                AccessLevel.FULL
            )
        ),
        NavItem(
            MR.images.ic_settlements,
            MR.strings.settlements,
            NavItemPosition.SETTLEMENTS,
            isAvailable = accessLevelUseCase.hasPermission(
                Section.SETTLEMENTS,
                AccessLevel.READ,
                AccessLevel.FULL
            )
        ),
        NavItem(
            MR.images.ic_reports,
            MR.strings.reports,
            NavItemPosition.REPORTS,
            isAvailable = accessLevelUseCase.hasPermission(
                Section.REPORTS,
                AccessLevel.READ
            )
        ),
        NavItem(
            MR.images.ic_payment_methods,
            MR.strings.payment_methods,
            NavItemPosition.PAYMENT_METHODS,
            isAvailable = accessLevelUseCase.hasPermission(
                Section.PAYMENT_METHODS,
                AccessLevel.READ
            )
        ),
        NavItem(
            MR.images.ic_team,
            MR.strings.team_management,
            NavItemPosition.TEAM_MANAGEMENT,
            isAvailable = accessLevelUseCase.hasPermission(
                Section.TEAM_MANAGEMENT,
                AccessLevel.READ
            )
        )
    )

    fun getSettingsItems() : List<SettingsItem> = listOf(
        SettingsItem(
            MR.images.lang_eng,
            MR.strings.english,
            SettingsPosition.LANGUAGE
        ),
        SettingsItem(
            MR.images.ic_help,
            MR.strings.help_center,
            SettingsPosition.HELP_CENTER
        ),
        SettingsItem(
            MR.images.ic_info,
            MR.strings.info,
            SettingsPosition.INFO
        )
    )

    fun getReportsItem(): NavItem = NavItem(
        MR.images.logout,
        MR.strings.reports,
        NavItemPosition.REPORTS
    )

    fun getOnlinePaymentsItem(): NavItem = NavItem(
        MR.images.logout,
        MR.strings.online_payments,
        NavItemPosition.ONLINE_PAYMENTS,
        isAvailable = accessLevelUseCase.hasPermission(
            Section.ONLINE_PAYMENTS,
            AccessLevel.FULL
        )
    )

    fun getHeaderPositions(): HashSet<Int> = hashSetOf(-1)
}