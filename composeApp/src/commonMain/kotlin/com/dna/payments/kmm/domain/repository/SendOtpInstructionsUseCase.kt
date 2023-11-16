package com.dna.payments.kmm.domain.repository

import com.dna.payments.kmm.domain.network.Response

interface SendOtpInstructionsUseCase {

    suspend fun sendInstructions(email: String): Response<Unit>
}