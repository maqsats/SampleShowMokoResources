package com.dna.payments.kmm.data.repository

import com.dna.payments.kmm.data.model.request.EmailVerification
import com.dna.payments.kmm.data.model.request.EmailVerificationRequest
import com.dna.payments.kmm.data.model.request.NewPasswordRequest
import com.dna.payments.kmm.data.model.request.SendInstructionRequest
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.ResetPasswordRepository
import com.dna.payments.kmm.utils.constants.Constants.BASE_URL
import com.dna.payments.kmm.utils.constants.Constants.CREDENTIALS_HEADER
import com.dna.payments.kmm.utils.extension.getBasicToken
import com.dna.payments.kmm.utils.extension.handleApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders

class DefaultResetPasswordRepository(
    private val httpClient: HttpClient
) : ResetPasswordRepository {

    override suspend fun sendInstructions(sendInstructionRequest: SendInstructionRequest): Response<Unit> =
        handleApiCall {
            httpClient.post {
                url("${BASE_URL}v1/password-reset")
                header(CREDENTIALS_HEADER, getBasicToken())
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(sendInstructionRequest)
            }.body()
        }

    override suspend fun verifyOtpCode(emailVerificationRequest: EmailVerificationRequest): Response<EmailVerification> =
        handleApiCall {
            httpClient.post {
                url("${BASE_URL}v1/validation")
                header(CREDENTIALS_HEADER, getBasicToken())
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(emailVerificationRequest)
            }.body()
        }

    override suspend fun changePassword(newPasswordRequest: NewPasswordRequest): Response<Unit> =
        handleApiCall {
            httpClient.post {
                url("${BASE_URL}v1/password")
                header(CREDENTIALS_HEADER, getBasicToken())
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(newPasswordRequest)
            }
        }
}