package com.dnapayments.mp.utils.biometry

import androidx.compose.runtime.Composable

@Composable
actual fun rememberBiometryAuthenticatorFactory(): BiometryAuthenticatorFactory {
    return BiometryAuthenticatorFactory { BiometryAuthenticator() }
}