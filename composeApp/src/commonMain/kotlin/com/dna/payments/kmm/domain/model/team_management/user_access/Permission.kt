package com.dna.payments.domain.presentation.team_management.user_access

data class Permission(
    val name: String,
    val key: String,
    val settingAccess: List<SettingAccess>
) {
    fun getValue(): String {
        return if (getCheckedSettingAccessType() == SettingAccessType.NO_ACCESS) "" else "$key.${getCheckedSettingAccessType().url}"
    }
    private fun getCheckedSettingAccessType(): SettingAccessType {
        val checkedSettingAccess = settingAccess.find { it.isChecked }
        return checkedSettingAccess?.type ?: SettingAccessType.NO_ACCESS
    }
}
