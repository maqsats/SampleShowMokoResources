package com.dna.payments.kmm.utils.biometry

import dev.icerock.moko.resources.desc.StringDesc

actual class BiometryAuthenticator {
    /**
     * Performs user authentication using biometrics-fingerprint/face scan-returns the result of the scan
     *
     * @param requestTitle - Text for title of request dialog
     * @param requestReason - Text describing the reason for confirmation via biometrics
     * @param failureButtonText - Text of the button to go to the backup verification method in
     * case of unsuccessful biometrics recognition
     * @param allowDeviceCredentials - Boolean value of device credentials availability,
     * if biometric permission is not granted user can authorise by device passcode
     *
     * @throws Exception if authentication failed
     *
     * @return true for successful confirmation of biometrics, false for unsuccessful confirmation
     */
    actual suspend fun checkBiometryAuthentication(
        requestTitle: StringDesc,
        requestReason: StringDesc,
        failureButtonText: StringDesc,
        allowDeviceCredentials: Boolean
    ) = false

    /**
     * Performs a biometric scan availability check
     *
     * @return true if it is possible to use a biometry, false - if it is not available
     */
    actual fun isBiometricAvailable() = false
}