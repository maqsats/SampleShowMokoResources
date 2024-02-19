package com.dnapayments.mp.domain.model.pos_payments

import androidx.compose.ui.graphics.Color
import com.dnapayments.mp.MR
import com.dnapayments.mp.presentation.theme.darkGreen
import com.dnapayments.mp.presentation.theme.darkGrey
import com.dnapayments.mp.presentation.theme.darkRed
import com.dnapayments.mp.presentation.theme.lightGreen
import com.dnapayments.mp.presentation.theme.lightGrey
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
