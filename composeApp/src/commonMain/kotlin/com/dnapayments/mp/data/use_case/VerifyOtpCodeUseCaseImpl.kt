package com.dnapayments.mp.data.use_case

import com.dnapayments.mp.data.model.request.EmailVerification
import com.dnapayments.mp.data.model.request.EmailVerificationRequest
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.ResetPasswordRepository
import com.dnapayments.mp.domain.use_case.VerifyOtpCodeUseCase

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