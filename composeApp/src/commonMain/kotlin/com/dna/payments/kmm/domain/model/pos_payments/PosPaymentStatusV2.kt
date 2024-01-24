package com.dna.payments.kmm.domain.model.pos_payments

import androidx.compose.ui.graphics.Color
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.presentation.theme.darkGreen
import com.dna.payments.kmm.presentation.theme.darkGrey
import com.dna.payments.kmm.presentation.theme.darkRed
import com.dna.payments.kmm.presentation.theme.lightGreen
import com.dna.payments.kmm.presentation.theme.lightGrey
import dev.icerock.moko.resources.ImageResource

enum class PosPaymentStatusV2(
    val key: String?,
    val displayName: String,
    val detailText: String?,
    val textColor: Color,
    val backgroundColor: Color,
    val imageResource: ImageResource? = null,
) {
    ALL(null, "All", null, Color.Unspecified, Color.Unspecified, null),
    AUTHORISED(
        key = "authorised",
        displayName = "Authorised",
        detailText = "Transaction authorized",
        darkGreen, lightGreen,
        MR.images.ic_success
    ),
    CHARGED(
        key = "charged",
        displayName = "Charged",
        detailText = "Payment charged",
        darkGreen, lightGreen,
        MR.images.ic_success
    ),
    DECLINED(
        key = "declined",
        displayName = "Declined",
        detailText = "Payment declined",
        darkRed, Color.Unspecified,
        MR.images.ic_cancel
    ),
    REFUNDED(
        key = "refunded",
        displayName = "Refunded",
        detailText = "Payment refunded",
        darkGrey, lightGrey,
        MR.images.ic_cancel
    ),
    CANCELLED(
        key = "cancelled",
        displayName = "Cancelled",
        detailText = "Payment cancelled",
        darkRed, Color.Unspecified,
        MR.images.ic_cancel
    );

    companion object {
        fun fromString(status: String): PosPaymentStatusV2 {
            return entries.find { it.key == status } ?: ALL
        }
    }
}
