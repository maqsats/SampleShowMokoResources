/*
 * Copyright 2023 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.dnapayments.mp.utils.permissions.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.dnapayments.mp.utils.permissions.ios.PermissionsController

@Composable
actual fun rememberPermissionsControllerFactory(): PermissionsControllerFactory {
    return remember {
        PermissionsControllerFactory {
            PermissionsController()
        }
    }
}
