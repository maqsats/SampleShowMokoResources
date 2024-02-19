/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.dnapayments.mp.utils.permissions.ios

import com.dnapayments.mp.utils.permissions.Permission
import com.dnapayments.mp.utils.permissions.PermissionState

interface PermissionsControllerProtocol {
    suspend fun providePermission(permission: Permission)
    suspend fun isPermissionGranted(permission: Permission): Boolean
    suspend fun getPermissionState(permission: Permission): PermissionState
    fun openAppSettings()
}
