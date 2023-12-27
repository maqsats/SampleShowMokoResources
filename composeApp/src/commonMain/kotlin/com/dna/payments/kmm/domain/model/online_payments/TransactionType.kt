package com.dna.payments.kmm.domain.model.online_payments;

import androidx.compose.ui.graphics.Color
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.presentation.theme.lightGreen
import com.dna.payments.kmm.presentation.theme.peachBlossom
import com.dna.payments.kmm.presentation.theme.skyBlue
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

enum class TransactionType(
    val value: String,
    val stringResource: StringResource,
    val key: String,
    val imageResource: ImageResource? = null,
    val backgroundColor: Color? = null,
) {
    SALE("Sale", MR.strings.sale, "SALE", MR.images.ic_sale, peachBlossom),
    AUTH("Authorisation Only", MR.strings.auth, "AUTH", MR.images.ic_key, skyBlue),
    PRE_AUTH("Pre-Authorisation", MR.strings.pre_auth, "PRE-AUTH", MR.images.ic_pre_key, skyBlue),
    ADJUSTMENT("Adjustment", MR.strings.adjustment, "ADJUSTMENT"),
    REFUND("Refund", MR.strings.refund, "REFUND", MR.images.ic_refund_gbp, lightGreen),
    VERIFICATION(
        "Verification",
        MR.strings.verification,
        "VERIFICATION",
        MR.images.ic_verification,
        skyBlue
    ),
    OTHER("Other", MR.strings.other, "OTHER"),
    PAYBYLINK("Pay by Link", MR.strings.pay_by_link, "PAYBYLINK"),
    RETAIL("Sale", MR.strings.sale, "RETAIL"),
    INIT("Init", MR.strings.init, "INIT"),
    TOKENIZATION("Tokenization", MR.strings.tokenization, "TOKENIZATION"),
    P2P("P2P", MR.strings.p2p, "P2P"),
    RECURRING("Recurring", MR.strings.recurring, "RECURRING", MR.images.ic_re_create, lightGreen);

    companion object {
        fun getProcessNewPaymentValues() = listOf(
            SALE,
            AUTH
        )

        fun getVirtualTerminalValues() = listOf(
            SALE,
            AUTH,
            VERIFICATION
        )

        fun getCreateNewLinkValues() = listOf(
            SALE,
            AUTH,
            PRE_AUTH,
            VERIFICATION
        )

        fun TransactionType?.isRefund(): Boolean {
            return this == TransactionType.REFUND
        }


        fun fromString(status: String? = null): TransactionType? {
            return values().find { it.value == status }
        }

        fun fromKey(status: String? = null): TransactionType {
            return values().find { it.key == status } ?: OTHER
        }
    }
}