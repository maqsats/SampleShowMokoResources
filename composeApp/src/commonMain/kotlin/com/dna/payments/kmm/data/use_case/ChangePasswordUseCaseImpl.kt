package com.dna.payments.kmm.data.use_case

import com.dna.payments.kmm.data.model.request.NewPasswordRequest
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.use_case.ChangePasswordUseCase
import com.dna.payments.kmm.domain.repository.ResetPasswordRepository

class ChangePasswordUseCaseImpl(private val resetPasswordRepository: ResetPasswordRepository) :
    ChangePasswordUseCase {

    override suspend operator fun invoke(
        email: String,
        password: String,
        verificationId: String
    ): Response<Unit> {
        return resetPasswordRepository.changePassword(
            NewPasswordRequest(
                email = email,
                password = password,
                verificationId = verificationId
            )
        )
    }
}