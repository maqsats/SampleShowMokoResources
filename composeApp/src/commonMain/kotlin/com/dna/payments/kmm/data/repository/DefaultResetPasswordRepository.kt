package com.dna.payments.kmm.data.repository

import com.dna.payments.kmm.data.model.request.EmailVerification
import com.dna.payments.kmm.data.model.request.EmailVerificationRequest
import com.dna.payments.kmm.data.model.request.NewPasswordRequest
import com.dna.payments.kmm.data.model.request.SendInstructionRequest
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.ResetPasswordRepository
import com.dna.payments.kmm.utils.constants.Constants
import com.dna.payments.kmm.utils.extension.handleApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters

class DefaultResetPasswordRepository(
    private val httpClient: HttpClient
) : ResetPasswordRepository {

    override suspend fun sendInstructions(sendInstructionRequest: SendInstructionRequest): Response<Unit> =
        handleApiCall {
            httpClient.post {
                url("https://test-portal.dnapayments.com/oppapi/client/v1/password-reset")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(sendInstructionRequest)
            }.body()
        }

    override suspend fun verifyOtpCode(emailVerificationRequest: EmailVerificationRequest): Response<EmailVerification> =
        handleApiCall {
            httpClient.submitForm(
                url = "${Constants.BASE_RESTORE_URL}v1/validation",
                formParameters = Parameters.build {
                    append("grant_type", Constants.GRANT_TYPE)
                    append("scope", Constants.SCOPE)
                    append("email", emailVerificationRequest.email)
                    append("key", emailVerificationRequest.key)
                    append("client_id", Constants.CLIENT_ID)
                    append("client_secret", Constants.CLIENT_SECRET)
                }
            )
        }

    override suspend fun changePassword(newPasswordRequest: NewPasswordRequest): Response<Unit> =
        handleApiCall {
            httpClient.submitForm(
                url = "${Constants.BASE_RESTORE_URL}/v1/password",
                formParameters = Parameters.build {
                    append("email", newPasswordRequest.email)
                    append("password", newPasswordRequest.password)
                    append("verificationId", newPasswordRequest.verificationId)
                }
            )
        }
}