package com.dna.payments.kmm.domain.model.team_management

import androidx.compose.ui.graphics.Color
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.presentation.theme.darkBlue
import com.dna.payments.kmm.presentation.theme.darkGreen
import com.dna.payments.kmm.presentation.theme.darkGrey
import com.dna.payments.kmm.presentation.theme.lightBlue
import com.dna.payments.kmm.presentation.theme.lightGreen
import com.dna.payments.kmm.presentation.theme.lightGrey
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource


enum class UserStatus(
    val value: String,
    val displayName: StringResource,
    val textColor: Color,
    val icon: ImageResource,
    val backgroundColor: Color
) {
    ACTIVE(
        "OK",
        MR.strings.active,
        darkGreen,
        MR.images.ic_success,
        lightGreen
    ),
    CHANGE_PASSWORD(
        "CHANGEPASSWORD",
        MR.strings.change_password,
        darkBlue,
        MR.images.ic_ready_to_charge,
        lightBlue
    ),
    BLOCK(
        "BLOCK",
        MR.strings.blocked,
        darkGrey,
        MR.images.ic_ready_to_charge,
        lightGrey
    ),
    VALIDATE_EMAIL(
        "VALIDATE_EMAIL",
        MR.strings.validate_email,
        darkBlue,
        MR.images.ic_ready_to_charge,
        lightBlue
    ),
    INVITED(
        "INVITED",
        MR.strings.invited,
        darkBlue,
        MR.images.ic_ready_to_charge,
        lightBlue
    );

    companion object {
        fun fromString(string: String): UserStatus {
            return entries.find { it.value == string }
                ?: ACTIVE
        }
    }
}