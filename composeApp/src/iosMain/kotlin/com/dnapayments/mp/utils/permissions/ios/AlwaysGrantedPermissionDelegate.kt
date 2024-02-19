/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.dnapayments.mp.utils.permissions.ios

import com.dnapayments.mp.utils.permissions.PermissionState

internal class AlwaysGrantedPermissionDelegate : PermissionDelegate {
    override suspend fun providePermission() = Unit

    override suspend fun getPermissionState(): PermissionState = PermissionState.Granted
}
