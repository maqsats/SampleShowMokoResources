package com.dna.payments.kmm.domain.model.status_summary

import androidx.compose.ui.graphics.Color
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.presentation.theme.darkBlue
import com.dna.payments.kmm.presentation.theme.darkGreen
import com.dna.payments.kmm.presentation.theme.darkGrey
import com.dna.payments.kmm.presentation.theme.darkRed
import com.dna.payments.kmm.presentation.theme.lightBlue
import com.dna.payments.kmm.presentation.theme.lightGreen
import com.dna.payments.kmm.presentation.theme.lightGrey
import com.dna.payments.kmm.presentation.theme.white
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource


enum class PaymentStatus(
    val value: String,
    val stringResource: StringResource,
    val textColor: Color,
    val backgroundColor: Color,
    val icon: ImageResource,
) {
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
    REJECT("Declined", MR.strings.declined, darkRed, white, MR.images.ic_cancel),
    FAILED("Failed", MR.strings.failed, darkRed, white, MR.images.ic_cancel),
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
        fun fromString(status: String? = "ALL"): PaymentStatus {
            return values().find { it.name == status } ?: ALL
        }
    }
}