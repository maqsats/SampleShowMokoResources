
package daniel.avila.rnm.kmm.utils.permissions.compose

import androidx.compose.runtime.Composable
import daniel.avila.rnm.kmm.utils.permissions.PermissionsController

@Suppress("FunctionNaming")
@Composable
expect fun BindEffect(permissionsController: PermissionsController)
