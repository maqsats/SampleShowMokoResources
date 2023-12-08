package com.dna.payments.kmm.domain.model.team_management


enum class UserStatus(
//    val value: String, val displayName: String, val textColor: Int,
//    val iconDrawable: Int,
//    val backgroundColor: Int
) {
    ACTIVE(
//        "OK", "Active",
//        R.color.green_tv,
//        R.drawable.ic_charge,
//        R.color.green_tv_bg
    ),
    CHANGE_PASSWORD(
//        "CHANGEPASSWORD", "Change password", R.color.grey_tv,
//        R.drawable.ic_close,
//        R.color.grey_tv_bg
    );

    companion object {
        fun fromString(string: String): UserStatus {
            return entries.find { it.name == string }
                ?: ACTIVE
        }
    }
}