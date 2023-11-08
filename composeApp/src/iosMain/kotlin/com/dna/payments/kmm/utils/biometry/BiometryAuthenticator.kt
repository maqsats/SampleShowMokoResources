package com.dna.payments.kmm.utils.biometry

import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.Foundation.NSError
import platform.LocalAuthentication.LAContext
import platform.LocalAuthentication.LAPolicyDeviceOwnerAuthentication
import platform.LocalAuthentication.LAPolicyDeviceOwnerAuthenticationWithBiometrics

actual class BiometryAuthenticator constructor() {
    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun checkBiometryAuthentication(
        requestTitle: StringDesc,
        requestReason: StringDesc,
        failureButtonText: StringDesc,
        allowDeviceCredentials: Boolean
    ): Boolean {
        val laContext = LAContext()
        laContext.setLocalizedFallbackTitle(failureButtonText.localized())

        val policy = if (allowDeviceCredentials) {
            LAPolicyDeviceOwnerAuthentication
        } else {
            LAPolicyDeviceOwnerAuthenticationWithBiometrics
        }

        val (canEvaluate: Boolean?, error: NSError?) = memScoped {
            val p = alloc<ObjCObjectVar<NSError?>>()
            val canEvaluate: Boolean? = runCatching {
                laContext.canEvaluatePolicy(policy, error = p.ptr)
            }.getOrNull()
            canEvaluate to p.value
        }

        if (error != null) throw error.toException()
        if (canEvaluate == null) return false

        return callbackToCoroutine { callback ->
            laContext.evaluatePolicy(
                policy = policy,
                localizedReason = requestReason.localized(),
                reply = mainContinuation { result: Boolean, error: NSError? ->
                    callback(result, error)
                }
            )
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun isBiometricAvailable(): Boolean {
        val laContext = LAContext()
        return laContext.canEvaluatePolicy(
            LAPolicyDeviceOwnerAuthenticationWithBiometrics,
            error = null
        )
    }
}