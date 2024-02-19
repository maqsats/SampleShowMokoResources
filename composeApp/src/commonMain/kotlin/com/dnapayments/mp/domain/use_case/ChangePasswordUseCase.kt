package com.dnapayments.mp.domain.use_case

import com.dnapayments.mp.domain.network.Response

interface ChangePasswordUseCase {

    suspend operator fun invoke(
        email: String,
        password: String,
        verificationId: String
    ): Response<Unit>
}