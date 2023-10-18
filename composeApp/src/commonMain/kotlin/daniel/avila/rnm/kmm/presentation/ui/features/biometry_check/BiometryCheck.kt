package daniel.avila.rnm.kmm.presentation.ui.features.biometry_check

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import daniel.avila.rnm.kmm.utils.biometry.BindBiometryAuthenticatorEffect
import daniel.avila.rnm.kmm.utils.biometry.BiometryAuthenticatorFactory
import daniel.avila.rnm.kmm.utils.biometry.rememberBiometryAuthenticatorFactory
import daniel.avila.rnm.kmm.utils.permissions.Permission
import daniel.avila.rnm.kmm.utils.permissions.PermissionsController
import daniel.avila.rnm.kmm.utils.permissions.compose.BindEffect
import daniel.avila.rnm.kmm.utils.permissions.compose.PermissionsControllerFactory
import daniel.avila.rnm.kmm.utils.permissions.compose.rememberPermissionsControllerFactory
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BiometryCheck(modifier: Modifier = Modifier) {
    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val controller: PermissionsController =
        remember(factory) { factory.createPermissionsController() }
    BindEffect(controller)


    val biometryFactory: BiometryAuthenticatorFactory = rememberBiometryAuthenticatorFactory()
    val biometryAuthenticator = biometryFactory.createBiometryAuthenticator()
    BindBiometryAuthenticatorEffect(biometryAuthenticator)
    val scope = rememberCoroutineScope()

    Box(modifier = modifier) {
        Button(onClick = {
            scope.launch {
                try {
                    val isSuccess = biometryAuthenticator.checkBiometryAuthentication(
                        requestTitle = "Biometry".desc(),
                        requestReason = "Just for test".desc(),
                        failureButtonText = "Oops".desc(),
                        allowDeviceCredentials = false // true - if biometric permission is not granted user can authorise by device creds
                    )

                    if (isSuccess) {
                        println("Success")
                    }
                } catch (throwable: Throwable) {
                    throwable.printStackTrace()
                }
            }
        }) {
            println("Click")
        }
    }
}