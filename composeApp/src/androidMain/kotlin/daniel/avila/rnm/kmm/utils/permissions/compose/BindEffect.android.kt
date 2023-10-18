
package daniel.avila.rnm.kmm.utils.permissions.compose

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import daniel.avila.rnm.kmm.utils.permissions.PermissionsController

@Suppress("FunctionNaming")
@Composable
actual fun BindEffect(permissionsController: PermissionsController) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val context: Context = LocalContext.current

    LaunchedEffect(permissionsController, lifecycleOwner, context) {
        val fragmentManager = (context as FragmentActivity).supportFragmentManager

        permissionsController.bind(lifecycleOwner.lifecycle, fragmentManager)
    }
}
