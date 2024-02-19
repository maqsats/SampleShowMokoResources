package com.dnapayments.mp.domain.model.payment_links

import androidx.compose.ui.graphics.Color
import com.dnapayments.mp.MR
import com.dnapayments.mp.presentation.theme.darkBlue
import com.dnapayments.mp.presentation.theme.darkGreen
import com.dnapayments.mp.presentation.theme.darkGrey
import com.dnapayments.mp.presentation.theme.lightBlue
import com.dnapayments.mp.presentation.theme.lightGrey
import com.dnapayments.mp.presentation.theme.outlineGreenColor
import com.dnapayments.mp.presentation.theme.white
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource


enum class PaymentLinkStatus(
    val value: String,
    val stringResource: StringResource,
    val stringResourceStatus: StringResource,
    val textColor: Color,
    val icon: ImageResource,
    val backgroundColor: Color,
    val iconEnd: ImageResource? = null
) {
    ALL(
        "All", MR.strings.all_statuses, MR.strings.all_statuses, outlineGreenColor,
        MR.images.ic_success,
        white
    ),
    PAID(
        "paid", MR.strings.paid, MR.strings.paid, darkBlue,
        MR.images.ic_success,
        lightBlue
    ),
    ACTIVE(
        "active", MR.strings.active, MR.strings.active, darkGreen,
        MR.images.ic_active,
        white
    ),
    ATTEMPTED(
        "attempted", MR.strings.attempted, MR.strings.attempted, darkGrey,
        MR.images.ic_attempted,
        lightGrey
    ),
    VIEWED(
        "viewed", MR.strings.active, MR.strings.viewed, darkGreen,
        MR.images.ic_active,
        white,
        MR.images.ic_viewed
    ),
    EXPIRED(
        "expired", MR.strings.expired, MR.strings.expired, darkGrey,
        MR.images.ic_cancel,
        lightGrey
    ),
    CANCELLED(
        "cancelled", MR.strings.cancelled, MR.strings.cancelled, darkGrey,
        MR.images.ic_cancel,
        lightGrey
    ),
    NOT_FOUND(
        "", MR.strings.empty_text, MR.strings.empty_text, white,
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