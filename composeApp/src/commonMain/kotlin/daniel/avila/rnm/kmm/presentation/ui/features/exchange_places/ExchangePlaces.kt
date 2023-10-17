package daniel.avila.rnm.kmm.presentation.ui.features.exchange_places

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import daniel.avila.rnm.kmm.utils.permissions.DeniedAlwaysException
import daniel.avila.rnm.kmm.utils.permissions.DeniedException
import daniel.avila.rnm.kmm.utils.permissions.Permission
import daniel.avila.rnm.kmm.utils.permissions.PermissionsController
import daniel.avila.rnm.kmm.utils.permissions.compose.BindEffect
import daniel.avila.rnm.kmm.utils.permissions.compose.PermissionsControllerFactory
import daniel.avila.rnm.kmm.utils.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ExchangePlaces(modifier: Modifier = Modifier) {
    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val controller: PermissionsController =
        remember(factory) { factory.createPermissionsController() }
    BindEffect(controller)
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    Box(modifier = modifier.fillMaxWidth()) {
        Button(
            onClick = {
                coroutineScope.launch {
                    println("requesting permissions")
                    try {
                        controller.providePermission(Permission.COARSE_LOCATION)
                        println("permissions granted")
                    } catch (deniedAlways: DeniedAlwaysException) {
                        println("permissions denied always")
                    } catch (denied: DeniedException) {
                        println("permissions denied")
                    }
                }
            }
        ) {
        }
    }
}