/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.dna.payments.kmm.utils.permissions.ios

import com.dna.payments.kmm.utils.permissions.Permission
import com.dna.payments.kmm.utils.permissions.PermissionState

interface PermissionsControllerProtocol {
    suspend fun providePermission(permission: Permission)
    suspend fun isPermissionGranted(permission: Permission): Boolean
    suspend fun getPermissionState(permission: Permission): PermissionState
    fun openAppSettings()
}
