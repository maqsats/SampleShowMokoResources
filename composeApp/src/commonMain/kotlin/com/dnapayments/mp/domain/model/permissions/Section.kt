package com.dnapayments.mp.domain.model.permissions

import kotlinx.serialization.Serializable

@Serializable
enum class Section(val value: String) {
    REPORTS("reports"),
    PAYMENT_METHODS("payment_methods"),
    TEAM_MANAGEMENT("teammates"),
    OVERVIEW("overview"),
    SETTLEMENTS("settlements"),
    INVOICES("invoices"),
    PAYMENT_LINKS("payment_links"),
    ONLINE_PAYMENTS("online_payments"),
    POS_PAYMENTS("pos_payments"),
    VIRTUAL_TERMINAL("virtual_terminal"),
}