package com.dna.payments.kmm.utils.extension

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.data.model.Error
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.utils.UiText
import com.dna.payments.kmm.utils.constants.Constants
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.utils.io.core.toByteArray
import kotlinx.serialization.json.Json
import okio.IOException
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


suspend inline fun <reified T> handleApiCall(apiCall: () -> HttpResponse): Response<T> =
    try {
        val response = apiCall()
        when (response.status.value) {
            in 400..499 -> {
                val error = Json
                    .decodeFromString(Error.serializer(), response.bodyAsText())
                if ((error.code == 86)) {
                    Response.TokenExpire
                } else {
                    Response.Error(UiText.DynamicString(error.message))
                }
            }
            in 200..299 -> {
                Response.Success(response.body())
            }
            else -> {
                Response.Error(UiText.StringResource(MR.strings.something_went_wrong))
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        e.catchError()
    }

fun <T> Throwable.catchError(): Response<T> {
    return when (this) {
        is IOException -> {
            Response.NetworkError
        }
        else -> {
            Response.Error(UiText.StringResource(MR.strings.something_went_wrong))
        }
    }
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

@OptIn(ExperimentalEncodingApi::class)
fun getBasicToken() = "Basic ${
    Base64.encode(
        "${Constants.CLIENT_ID}:${Constants.CLIENT_SECRET}".toByteArray()
    )
}"