package com.dnapayments.mp.utils.biometry

import androidx.compose.runtime.Composable

fun interface BiometryAuthenticatorFactory {
    fun createBiometryAuthenticator(): BiometryAuthenticator
}

@Composable
expect fun rememberBiometryAuthenticatorFactory(): BiometryAuthenticatorFactory