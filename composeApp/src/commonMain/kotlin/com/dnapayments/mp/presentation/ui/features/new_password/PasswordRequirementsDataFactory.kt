package com.dnapayments.mp.presentation.ui.features.new_password

import com.dnapayments.mp.MR
import com.dnapayments.mp.domain.interactors.validation.ValidatePassword
import com.dnapayments.mp.domain.model.new_password.PasswordRequirement
import com.dnapayments.mp.domain.model.new_password.PasswordRequirementType
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object PasswordRequirementsDataFactory : KoinComponent {

    private val validatePassword: ValidatePassword by inject()

    fun getRequirementList(
        password: String = "",
        confirmPassword: String = ""
    ): List<PasswordRequirement> {
        return listOf(
            PasswordRequirement(
                PasswordRequirementType.AT_LEAST_EIGHT_CHARACTERS,
                MR.strings.at_least_8_length_char,
                validatePassword.containsAtLeastEightChars(password)
            ),
            PasswordRequirement(
                PasswordRequirementType.AT_LEAST_ONE_DIGIT_AND_ONE_LETTER,
                MR.strings.at_one_digit_or_letter,
                validatePassword.containsAtLeastOneDigitAndOneLetter(password)
            ),
            PasswordRequirement(
                PasswordRequirementType.AT_LEAST_ONE_SPECIAL_CHARACTER,
                MR.strings.at_least_1_special_char,
                validatePassword.containsSpecialChar(password)
            ),
            PasswordRequirement(
                PasswordRequirementType.AT_LEAST_ONE_UPPERCASE_CHAR,
                MR.strings.at_least_1_uppercase,
                validatePassword.containsUpperCase(password)
            ),
            PasswordRequirement(
                PasswordRequirementType.AT_LEAST_ONE_LOWERCASE_CHAR,
                MR.strings.at_least_1_lowercase,
                validatePassword.containsLowerCase(password)
            ),
            PasswordRequirement(
                PasswordRequirementType.MATCH_PASSWORDS,
                MR.strings.password_must_match,
                validatePassword.passwordsMatch(password, confirmPassword)
            )
        )
    }
}