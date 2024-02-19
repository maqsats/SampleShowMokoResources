package com.dnapayments.mp.utils.biometry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.dnapayments.mp.utils.biometry.BiometryAuthenticatorFactory

@Composable
actual fun rememberBiometryAuthenticatorFactory(): BiometryAuthenticatorFactory {
    return remember {
        BiometryAuthenticatorFactory { com.dnapayments.mp.utils.biometry.BiometryAuthenticator() }
    }
}