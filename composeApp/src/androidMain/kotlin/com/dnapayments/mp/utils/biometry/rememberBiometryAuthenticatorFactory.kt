package com.dnapayments.mp.utils.biometry

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.dnapayments.mp.utils.biometry.BiometryAuthenticatorFactory

@Composable
actual fun rememberBiometryAuthenticatorFactory(): BiometryAuthenticatorFactory {
    val context: Context = LocalContext.current
    return remember(context) {
        BiometryAuthenticatorFactory {
            com.dnapayments.mp.utils.biometry.BiometryAuthenticator(applicationContext = context.applicationContext)
        }
    }
}