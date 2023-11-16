package com.dna.payments.kmm.data.repository

import com.dna.payments.kmm.data.model.request.EmailVerification
import com.dna.payments.kmm.data.model.request.EmailVerificationRequest
import com.dna.payments.kmm.data.model.request.NewPasswordRequest
import com.dna.payments.kmm.data.model.request.SendInstructionRequest
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.ResetPasswordRepository
import com.dna.payments.kmm.utils.extension.handleApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.http.Parameters

class DefaultResetPasswordRepository(
    private val httpClient: HttpClient
) : ResetPasswordRepository {

    override suspend fun sendInstructions(sendInstructionRequest: SendInstructionRequest): Response<Unit> =
        handleApiCall {
            httpClient.submitForm(
                url = "/v1/password-reset",
                formParameters = Parameters.build {
                    append("email", sendInstructionRequest.email)
                }
            )
        }

    override suspend fun verifyOtpCode(emailVerificationRequest: EmailVerificationRequest): Response<EmailVerification> =
        handleApiCall {
            httpClient.submitForm(
                url = "/v1/validation",
                formParameters = Parameters.build {
                    append("email", emailVerificationRequest.email)
                    append("key", emailVerificationRequest.key)
                }
            )
        }

    override suspend fun changePassword(newPasswordRequest: NewPasswordRequest): Response<Unit> =
        handleApiCall {
            httpClient.submitForm(
                url = "/v1/password",
                formParameters = Parameters.build {
                    append("email", newPasswordRequest.email)
                    append("password", newPasswordRequest.password)
                    append("verificationId", newPasswordRequest.verificationId)
                }
            )
        }
}