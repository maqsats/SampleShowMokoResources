
package com.dna.payments.kmm.utils.permissions.compose

import androidx.compose.runtime.Composable
import com.dna.payments.kmm.utils.permissions.PermissionsController

// on iOS side we should not do anything to prepare PermissionsController to work
@Suppress("FunctionNaming")
@Composable
actual fun BindEffect(permissionsController: PermissionsController) = Unit
