package com.dnapayments.mp.domain.repository

import com.dnapayments.mp.data.model.request.EmailVerification
import com.dnapayments.mp.data.model.request.EmailVerificationRequest
import com.dnapayments.mp.data.model.request.NewPasswordRequest
import com.dnapayments.mp.data.model.request.SendInstructionRequest
import com.dnapayments.mp.domain.network.Response

interface ResetPasswordRepository {

    suspend fun sendInstructions(
        sendInstructionRequest: SendInstructionRequest
    ): Response<Unit>

    suspend fun verifyOtpCode(
        emailVerificationRequest: EmailVerificationRequest
    ): Response<EmailVerification>

    suspend fun changePassword(
        newPasswordRequest: NewPasswordRequest
    ): Response<Unit>
}