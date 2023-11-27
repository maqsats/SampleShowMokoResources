package com.dna.payments.kmm.domain.repository

import com.dna.payments.kmm.data.model.request.EmailVerification
import com.dna.payments.kmm.domain.network.Response

interface ChangePasswordUseCase {

    suspend operator fun invoke(
        email: String,
        password: String,
        verificationId: String
    ): Response<Unit>
}