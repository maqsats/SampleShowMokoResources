package com.dna.payments.kmm.utils.extension

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.data.model.Error
import com.dna.payments.kmm.domain.interactors.use_cases.currency.CurrencyHelper
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.DatePickerConstants.DATE_FORMAT_WITH_HOUR
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.DatePickerConstants.dateFormatter
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.DatePickerConstants.dateFormatterHM
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.DatePickerConstants.dateFormatterOnlyHM
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.DatePickerConstants.dateFormatterWithYear
import com.dna.payments.kmm.domain.model.currency.Currency
import com.dna.payments.kmm.domain.model.date_picker.DatePickerPeriod
import com.dna.payments.kmm.domain.model.date_picker.DateSelection
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.utils.UiText
import com.dna.payments.kmm.utils.constants.Constants
import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeTz
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

fun Modifier.bottomShadow(shadow: Dp) =
    this
        .clip(GenericShape { size, _ ->
            lineTo(size.width, 0f)
            lineTo(size.width, Float.MAX_VALUE)
            lineTo(0f, Float.MAX_VALUE)
        })
        .shadow(shadow)

@OptIn(ExperimentalEncodingApi::class)
fun getBasicToken() = "Basic ${
    Base64.encode(
        "${Constants.CLIENT_ID}:${Constants.CLIENT_SECRET}".toByteArray()
    )
}"

fun String.toBearerToken() = "Bearer $this"

fun DateTime?.isSameDayAs(other: DateTime): Boolean {
    return this?.dayOfYear == other.dayOfYear && this.year == other.year
}

fun DateTime.getFormatted(): String {
    return this.format(dateFormatter)
}

fun DateTime?.ddMmYyyy(): String {
    return this?.format(dateFormatterWithYear) ?: ""
}

fun getDefaultDateRange(): Pair<DatePickerPeriod, DateSelection> = Pair(
    DatePickerPeriod.TODAY,
    DateSelection(
        DateTime.now().startOfDay,
        DateTime.now().endOfDay
    )
)

fun DateTime?.getFormattedHM(): String {
    return this?.format(dateFormatterHM) ?: ""
}

fun String.cutSubstringAfterDot(): String {
    val dotIndex = indexOf('.')
    return if (dotIndex != -1) {
        substring(0, dotIndex).replace("T", " ")
    } else {
        this
    }
}

fun DateTime?.daysBetween(other: DateTime?): Int {
    return if (this == null || other == null) 0 else {
        val startMillis = this.unixMillisLong
        val endMillis = other.unixMillisLong
        val millisecondsPerDay = 24 * 60 * 60 * 1000
        ((endMillis - startMillis) / millisecondsPerDay).toInt()
    }
}

fun DateTime?.convertToServerFormat(): String {
    return this?.format(DATE_FORMAT_WITH_HOUR) ?: ""
}

fun Double.toMoneyString(): String {
    return if (this >= 1000000) {
        "${(this / 1000000).toInt()}m"
    } else if (this >= 1000) {
        "${(this / 1000).toInt()}k"
    } else {
        this.toInt().toString()
    }
}


fun String.addSpace(): String {
    return "$this "
}

fun Int.toMoneyString(): String {
    return if (this >= 1000000) {
        "${(this / 1000000)}m"
    } else if (this >= 1000) {
        "${(this / 1000)}k"
    } else {
        this.toString()
    }
}

fun DateTime?.getFormattedOnlyHM(): String {
    return this?.format(dateFormatterOnlyHM) ?: ""
}

fun Modifier.shimmerLoadingAnimation(
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
): Modifier {
    return composed {

        val shimmerColors = listOf(
            Color.White.copy(alpha = 0.3f),
            Color.White.copy(alpha = 0.5f),
            Color.White.copy(alpha = 1.0f),
            Color.White.copy(alpha = 0.5f),
            Color.White.copy(alpha = 0.3f),
        )

        val transition = rememberInfiniteTransition(label = "")

        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = durationMillis,
                    easing = LinearEasing,
                ),
                repeatMode = RepeatMode.Restart,
            ),
            label = "Shimmer loading animation",
        )

        this.background(
            brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
                end = Offset(x = translateAnimation.value, y = angleOfAxisY),
            ),
        )
    }
}

fun String.capitalizeFirstLetter(): String {
    return if (isNotEmpty()) {
        this[0].uppercaseChar() + substring(1).lowercase()
    } else {
        this
    }
}

fun DateTimeTz.isEqual(other: DateTime): Boolean {
    return this.year == other.year && this.month == other.month && this.dayOfMonth == other.dayOfMonth
}

fun List<Currency>.findIndexOfDefaultCurrency(
    defaultCurrency: String
): Int {
    val index = indexOfFirst { it.name == defaultCurrency }
    return if (index != -1) index else 0
}


fun Double.toMoneyString(currency: String): String {
    val intValue = this.toLong()
    val decimalValue = ((this - intValue) * 100).toInt()

    val integralPart = addCommas(intValue)
    val decimalPart = decimalValue.toString().padStart(2, '0')

    return "${CurrencyHelper(currency)}$integralPart.${decimalPart.first()}"
}

fun addCommas(number: Long): String {
    val str = number.toString()
    val result = StringBuilder()

    for (i in str.indices.reversed()) {
        result.insert(0, str[i])
        if (i > 0 && (str.length - i) % 3 == 0) {
            result.insert(0, ',')
        }
    }

    return result.toString()
}

fun String.toCurrencySymbol(): String {
    return when (this) {
        "GBP" -> "£"
        "USD" -> "$"
        "EUR" -> "€"
        else -> this // Return the input code if no match is found
    }
}