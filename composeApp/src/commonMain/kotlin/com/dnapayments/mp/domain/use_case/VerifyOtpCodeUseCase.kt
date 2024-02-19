package com.dnapayments.mp.domain.use_case

import com.dnapayments.mp.data.model.request.EmailVerification
import com.dnapayments.mp.domain.network.Response

interface VerifyOtpCodeUseCase {

    suspend operator fun invoke(email: String, code: String): Response<EmailVerification>
}