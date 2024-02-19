package com.dnapayments.mp.domain.model.new_password

enum class PasswordRequirementType {
    AT_LEAST_EIGHT_CHARACTERS,
    AT_LEAST_ONE_DIGIT_AND_ONE_LETTER,
    AT_LEAST_ONE_SPECIAL_CHARACTER,
    AT_LEAST_ONE_UPPERCASE_CHAR,
    AT_LEAST_ONE_LOWERCASE_CHAR,
    MATCH_PASSWORDS
}