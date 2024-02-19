/*
 * Copyright 2023 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.dnapayments.mp.utils.permissions.compose

import androidx.compose.runtime.Composable
import com.dnapayments.mp.utils.permissions.PermissionsController

fun interface PermissionsControllerFactory {
    fun createPermissionsController(): PermissionsController
}

@Composable
expect fun rememberPermissionsControllerFactory(): PermissionsControllerFactory
