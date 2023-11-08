package com.dna.payments.kmm.utils.biometry

import androidx.compose.runtime.Composable

// on iOS side we should not do anything to prepare BiometryAuthenticator to work
@Suppress("FunctionNaming")
@Composable
actual fun BindBiometryAuthenticatorEffect(biometryAuthenticator: BiometryAuthenticator) = Unit