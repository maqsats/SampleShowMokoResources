package com.dna.payments.kmm.domain.use_case

import com.dna.payments.kmm.domain.network.Response

interface ChangePasswordUseCase {

    suspend operator fun invoke(
        email: String,
        password: String,
        verificationId: String
    ): Response<Unit>
}