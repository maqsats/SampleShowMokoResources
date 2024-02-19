package com.dnapayments.mp.domain.use_case

import com.dnapayments.mp.domain.network.Response

interface SendOtpInstructionsUseCase {

    suspend operator fun invoke(email: String): Response<Unit>
}