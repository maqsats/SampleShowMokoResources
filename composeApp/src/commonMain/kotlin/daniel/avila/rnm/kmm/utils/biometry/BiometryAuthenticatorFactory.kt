package daniel.avila.rnm.kmm.utils.biometry

import androidx.compose.runtime.Composable

fun interface BiometryAuthenticatorFactory {
    fun createBiometryAuthenticator(): BiometryAuthenticator
}

@Composable
expect fun rememberBiometryAuthenticatorFactory(): BiometryAuthenticatorFactory