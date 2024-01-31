package com.dna.payments.kmm.domain.model.currency

data class Currency(
    val name: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Currency

        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}