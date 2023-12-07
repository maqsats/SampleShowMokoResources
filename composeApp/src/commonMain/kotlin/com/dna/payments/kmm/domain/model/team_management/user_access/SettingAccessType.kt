package com.dna.payments.domain.presentation.team_management.user_access

enum class SettingAccessType(val key: String, val url: String) {
    READ_ONLY("Read only", "read"),
    NO_ACCESS("No access", "no_access"),
    FULL_ACCESS("Full access", "full")
}