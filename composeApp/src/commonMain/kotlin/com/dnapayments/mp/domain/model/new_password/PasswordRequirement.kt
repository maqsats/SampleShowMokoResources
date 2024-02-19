package com.dnapayments.mp.domain.model.new_password

import dev.icerock.moko.resources.StringResource

data class PasswordRequirement(
    val type: PasswordRequirementType,
    val name: StringResource,
    val isValid: Boolean
)
