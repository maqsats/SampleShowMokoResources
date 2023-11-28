package com.dna.payments.kmm.data.use_case

import com.dna.payments.kmm.data.model.request.SendInstructionRequest
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.ResetPasswordRepository
import com.dna.payments.kmm.domain.use_case.SendOtpInstructionsUseCase

class SendOtpInstructionsUseCaseImpl(private val resetPasswordRepository: ResetPasswordRepository) :
    SendOtpInstructionsUseCase {

    override suspend operator fun invoke(email: String): Response<Unit> {
        return resetPasswordRepository.sendInstructions(SendInstructionRequest(email))
    }
}