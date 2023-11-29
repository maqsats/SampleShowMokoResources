package com.dna.payments.kmm.domain.use_case

import com.dna.payments.kmm.domain.network.Response

interface SendOtpInstructionsUseCase {

    suspend operator fun invoke(email: String): Response<Unit>
}