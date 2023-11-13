package com.dna.payments.kmm.domain.model.permissions

import kotlinx.serialization.Serializable

@Serializable
enum class AccessLevel(val value: String) {
    READ("read"),
    FULL("full"),
    CREATE("create"),
    REFUNDS("refunds"),
    UPDATE("update"),
    DELETE("delete"),
    NO_ACCESS("no_access")
}