package com.dna.payments.kmm.data.repository

import com.dna.payments.kmm.data.model.request.EmailVerification
import com.dna.payments.kmm.data.model.request.EmailVerificationRequest
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.ResetPasswordRepository
import com.dna.payments.kmm.domain.repository.VerifyOtpCodeUseCase

class VerifyOtpCodeUseCaseImpl(private val resetPasswordRepository: ResetPasswordRepository) :
    VerifyOtpCodeUseCase {

    override suspend operator fun invoke(email: String, code: String): Response<EmailVerification> {
        return resetPasswordRepository.verifyOtpCode(
            EmailVerificationRequest(
                email = email,
                key = code
            )
        )
    }
}