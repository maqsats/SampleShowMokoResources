package com.dna.payments.kmm.domain.model.payment_links

import androidx.compose.ui.graphics.Color
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.presentation.theme.darkBlue
import com.dna.payments.kmm.presentation.theme.darkGrey
import com.dna.payments.kmm.presentation.theme.lightBlue
import com.dna.payments.kmm.presentation.theme.lightGrey
import com.dna.payments.kmm.presentation.theme.outlineGreenColor
import com.dna.payments.kmm.presentation.theme.white
import dev.icerock.moko.resources.ImageResource


enum class PaymentLinkStatus(
    val value: String, val displayName: String, val textColor: Color,
    val icon: ImageResource,
    val backgroundColor: Color,
    val iconEnd: ImageResource? = null
) {
    ALL(
        "All", "All", outlineGreenColor,
        MR.images.ic_success,
        white
    ),
    PAID(
        "paid", "Paid", darkBlue,
        MR.images.ic_success,
        lightBlue
    ),
    ACTIVE(
        "active", "Active", outlineGreenColor,
        MR.images.ic_active,
        white
    ),
    ATTEMPTED(
        "attempted", "Attempted", darkGrey,
        MR.images.ic_attempted,
        lightGrey
    ),
    VIEWED(
        "viewed", "Active", outlineGreenColor,
        MR.images.ic_active,
        white,
        MR.images.ic_viewed
    ),
    EXPIRED(
        "expired", "Expired", darkGrey,
        MR.images.ic_cancel,
        lightGrey
    ),
    CANCELLED(
        "cancelled", "Cancelled", darkGrey,
        MR.images.ic_cancel,
        lightGrey
    ),
    NOT_FOUND(
        "", "", white,
        MR.images.ic_cancel,
        white
    );

    companion object {
        fun fromString(string: String): PaymentLinkStatus {
            return entries.find { it.value == string }
                ?: NOT_FOUND
        }
    }
}