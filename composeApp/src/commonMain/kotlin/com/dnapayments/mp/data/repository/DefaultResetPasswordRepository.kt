package com.dnapayments.mp.data.repository

import com.dnapayments.mp.data.model.request.EmailVerification
import com.dnapayments.mp.data.model.request.EmailVerificationRequest
import com.dnapayments.mp.data.model.request.NewPasswordRequest
import com.dnapayments.mp.data.model.request.SendInstructionRequest
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.ResetPasswordRepository
import com.dnapayments.mp.utils.constants.Constants.BASE_URL
import com.dnapayments.mp.utils.constants.Constants.CREDENTIALS_HEADER
import com.dnapayments.mp.utils.extension.getBasicToken
import com.dnapayments.mp.utils.extension.handleApiCall
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