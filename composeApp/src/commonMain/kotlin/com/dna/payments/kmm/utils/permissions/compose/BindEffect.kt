
package com.dna.payments.kmm.utils.permissions.compose

import androidx.compose.runtime.Composable
import com.dna.payments.kmm.utils.permissions.PermissionsController

@Suppress("FunctionNaming")
@Composable
expect fun BindEffect(permissionsController: PermissionsController)
