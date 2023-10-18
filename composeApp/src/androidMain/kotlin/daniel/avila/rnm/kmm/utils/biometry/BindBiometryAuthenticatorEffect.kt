package daniel.avila.rnm.kmm.utils.biometry

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner

@Suppress("FunctionNaming")
@Composable
actual fun BindBiometryAuthenticatorEffect(biometryAuthenticator: BiometryAuthenticator) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val context: Context = LocalContext.current

    LaunchedEffect(biometryAuthenticator, lifecycleOwner, context) {
        val fragmentManager: FragmentManager = (context as FragmentActivity).supportFragmentManager

        biometryAuthenticator.bind(lifecycleOwner.lifecycle, fragmentManager)
    }
}