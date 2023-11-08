package com.dna.payments.kmm.utils.biometry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun rememberBiometryAuthenticatorFactory(): BiometryAuthenticatorFactory {
    return remember {
        BiometryAuthenticatorFactory { BiometryAuthenticator() }
    }
}