package com.dnapayments.mp.domain.model.payment_methods.setting

enum class SettingStatus(val status: String) {
    ACTIVE("active"),
    UNAVAILABLE("unavailable"),
    DISABLED("disabled")
}