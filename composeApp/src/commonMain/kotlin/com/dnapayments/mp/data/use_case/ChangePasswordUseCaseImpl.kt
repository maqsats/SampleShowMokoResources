package com.dnapayments.mp.data.use_case

import com.dnapayments.mp.data.model.request.NewPasswordRequest
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.use_case.ChangePasswordUseCase
import com.dnapayments.mp.domain.repository.ResetPasswordRepository

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