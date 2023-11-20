package com.dna.payments.kmm.domain.repository

import com.dna.payments.kmm.data.model.request.EmailVerification
import com.dna.payments.kmm.data.model.request.EmailVerificationRequest
import com.dna.payments.kmm.data.model.request.NewPasswordRequest
import com.dna.payments.kmm.data.model.request.SendInstructionRequest
import com.dna.payments.kmm.domain.network.Response

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