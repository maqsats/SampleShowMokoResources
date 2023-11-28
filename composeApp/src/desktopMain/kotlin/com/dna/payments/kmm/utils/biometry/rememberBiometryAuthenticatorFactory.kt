package com.dna.payments.kmm.utils.biometry

import androidx.compose.runtime.Composable

@Composable
actual fun rememberBiometryAuthenticatorFactory(): BiometryAuthenticatorFactory {
    return BiometryAuthenticatorFactory { BiometryAuthenticator() }
}