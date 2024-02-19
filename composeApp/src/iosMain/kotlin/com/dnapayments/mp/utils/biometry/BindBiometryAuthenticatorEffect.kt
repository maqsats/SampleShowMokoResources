package com.dnapayments.mp.utils.biometry

import androidx.compose.runtime.Composable
import com.dnapayments.mp.utils.biometry.BiometryAuthenticator

// on iOS side we should not do anything to prepare BiometryAuthenticator to work
@Suppress("FunctionNaming")
@Composable
actual fun BindBiometryAuthenticatorEffect(biometryAuthenticator: BiometryAuthenticator) = Unit