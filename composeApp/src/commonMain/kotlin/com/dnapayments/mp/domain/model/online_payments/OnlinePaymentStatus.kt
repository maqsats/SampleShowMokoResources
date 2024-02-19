package com.dnapayments.mp.domain.model.online_payments

import androidx.compose.ui.graphics.Color
import com.dnapayments.mp.MR
import com.dnapayments.mp.domain.model.payment_status.PaymentStatus
import com.dnapayments.mp.presentation.theme.darkBlue
import com.dnapayments.mp.presentation.theme.darkGreen
import com.dnapayments.mp.presentation.theme.darkGrey
import com.dnapayments.mp.presentation.theme.darkRed
import com.dnapayments.mp.presentation.theme.lightBlue
import com.dnapayments.mp.presentation.theme.lightGreen
import com.dnapayments.mp.presentation.theme.lightGrey
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource


enum class OnlinePaymentStatus(
    val value: String,
    val stringResource: StringResource,
    val textColor: Color,
    val backgroundColor: Color,
    val icon: ImageResource,
) : PaymentStatus {
    ALL(
        "Total transactions",
        MR.strings.all_statuses,
        darkGrey,
        lightGrey,
        MR.images.ic_ready_to_charge
    ),
    AUTH("Pending", MR.strings.pending, darkGreen, lightGreen, MR.images.ic_ready_to_charge),
    CREATED("Created", MR.strings.created, darkGrey, lightGrey, MR.images.ic_ready_to_charge),
    CREDITED("Credited", MR.strings.credited, darkGrey, lightGrey, MR.images.ic_arrow_right_small),
    CHARGE("Charged", MR.strings.charged, darkGreen, lightGreen, MR.images.ic_success),
    CANCEL("Cancelled", MR.strings.cancelled, darkGrey, lightGrey, MR.images.ic_cancel),
    REFUND("Refunded", MR.strings.refunded, darkGrey, lightGrey, MR.images.ic_restore),
    REJECT("Declined", MR.strings.declined, darkRed, Color.Unspecified, MR.images.ic_cancel),
    FAILED("Failed", MR.strings.failed, darkRed, Color.Unspecified, MR.images.ic_cancel),
    NEW("New", MR.strings.new_status, darkGrey, lightGrey, MR.images.ic_ready_to_charge),
    THREE_D_SECURE(
        "3D Secure",
        MR.strings.three_d_secure,
        darkBlue,
        lightBlue,
        MR.images.ic_ready_to_charge
    ),
    TOKENIZED("Tokenized", MR.strings.tokenized, darkBlue, lightBlue, MR.images.ic_ready_to_charge),
    VERIFIED("Verified", MR.strings.verified, darkGreen, lightGreen, MR.images.ic_success),
    PROCESSING(
        "Processing",
        MR.strings.processing,
        darkBlue,
        lightBlue,
        MR.images.ic_ready_to_charge
    ),
    ABANDONED("Abandoned", MR.strings.abandoned, darkGrey, lightGrey, MR.images.ic_eye_closed);

    companion object {
        fun fromString(status: String? = "ALL"): OnlinePaymentStatus {
            return entries.find { it.name == status } ?: ALL
        }

        fun getMainStatuses() = listOf(
            CHARGE,
            REFUND,
            AUTH,
            REJECT
        )
    }
}