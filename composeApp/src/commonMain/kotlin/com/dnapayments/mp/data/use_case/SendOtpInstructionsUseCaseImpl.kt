package com.dnapayments.mp.data.use_case

import com.dnapayments.mp.data.model.request.SendInstructionRequest
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.ResetPasswordRepository
import com.dnapayments.mp.domain.use_case.SendOtpInstructionsUseCase

class SendOtpInstructionsUseCaseImpl(private val resetPasswordRepository: ResetPasswordRepository) :
    SendOtpInstructionsUseCase {

    override suspend operator fun invoke(email: String): Response<Unit> {
        return resetPasswordRepository.sendInstructions(SendInstructionRequest(email))
    }
}