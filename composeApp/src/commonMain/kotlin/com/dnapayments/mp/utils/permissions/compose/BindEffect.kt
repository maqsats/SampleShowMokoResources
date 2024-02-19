
package com.dnapayments.mp.utils.permissions.compose

import androidx.compose.runtime.Composable
import com.dnapayments.mp.utils.permissions.PermissionsController

@Suppress("FunctionNaming")
@Composable
expect fun BindEffect(permissionsController: PermissionsController)
