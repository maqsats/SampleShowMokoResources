package com.dna.payments.kmm.domain.interactors.use_cases.drawer

import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.interactors.use_cases.access_level.AccessLevelUseCase
import com.dna.payments.kmm.domain.model.nav_item.NavItem
import com.dna.payments.kmm.domain.model.nav_item.NavItemPosition
import com.dna.payments.kmm.domain.model.permissions.AccessLevel
import com.dna.payments.kmm.domain.model.permissions.Section
import org.koin.core.component.KoinComponent

class DrawerUseCase(private val accessLevelUseCase: AccessLevelUseCase) : KoinComponent {

    fun getNavItemList(): List<NavItem> = arrayListOf(
        NavItem(
            MR.images.logout,
            MR.strings.overview,
            NavItemPosition.OVERVIEW,
            true,
            isAvailable = accessLevelUseCase.hasPermission(
                Section.OVERVIEW,
                AccessLevel.READ
            )
        ),
        NavItem(
            MR.images.logout,
            MR.strings.pos_payments,
            NavItemPosition.POS_PAYMENTS,
            isAvailable = accessLevelUseCase.hasPermission(
                Section.POS_PAYMENTS,
                AccessLevel.FULL,
                AccessLevel.READ
            )
        ),
        NavItem(
            MR.images.logout,
            MR.strings.online_payments,
            NavItemPosition.ONLINE_PAYMENTS,
            isAvailable = accessLevelUseCase.hasPermission(
                Section.ONLINE_PAYMENTS,
                AccessLevel.FULL,
                AccessLevel.READ
            )
        ),
        NavItem(
            MR.images.logout,
            MR.strings.payment_links,
            NavItemPosition.PAYMENT_LINKS,
            isAvailable = accessLevelUseCase.hasPermission(
                Section.PAYMENT_LINKS,
                AccessLevel.READ,
                AccessLevel.FULL
            )
        ),
        NavItem(
            MR.images.logout,
            MR.strings.virtual_terminal,
            NavItemPosition.VIRTUAL_TERMINAL,
            isAvailable = accessLevelUseCase.hasPermission(
                Section.VIRTUAL_TERMINAL,
                AccessLevel.READ,
                AccessLevel.FULL
            )
        ),
        NavItem(
            MR.images.logout,
            MR.strings.settlements,
            NavItemPosition.SETTLEMENTS,
            isAvailable = accessLevelUseCase.hasPermission(
                Section.SETTLEMENTS,
                AccessLevel.READ,
                AccessLevel.FULL
            )
        ),
        NavItem(
            MR.images.logout,
            MR.strings.reports,
            NavItemPosition.REPORTS,
            isAvailable = accessLevelUseCase.hasPermission(
                Section.REPORTS,
                AccessLevel.READ
            )
        ),
        NavItem(
            MR.images.logout,
            MR.strings.payment_methods,
            NavItemPosition.PAYMENT_METHODS,
            isAvailable = accessLevelUseCase.hasPermission(
                Section.PAYMENT_METHODS,
                AccessLevel.READ
            )
        ),
        NavItem(
            MR.images.logout,
            MR.strings.team_management,
            NavItemPosition.TEAM_MANAGEMENT,
            isAvailable = accessLevelUseCase.hasPermission(
                Section.TEAM_MANAGEMENT,
                AccessLevel.READ
            )
        ),
        NavItem(
            MR.images.logout,
            MR.strings.help_center,
            NavItemPosition.HELP_CENTER
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