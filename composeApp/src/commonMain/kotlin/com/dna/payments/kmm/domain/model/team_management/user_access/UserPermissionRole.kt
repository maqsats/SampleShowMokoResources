package com.dna.payments.domain.presentation.team_management.user_access

data class UserPermissionRole(
    val name: String,
    val userPermissionRoleOption: UserPermissionRoleOption,
    var isChecked: Boolean = false,
    val permissionTypeList: List<PermissionType>
)