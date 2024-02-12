package com.dna.payments.kmm.utils.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import com.dna.payments.kmm.MR
import dev.icerock.moko.resources.StringResource


interface NavigatorResult

data class OnlinePaymentNavigatorResult(
    val value: OnlinePaymentNavigatorResultType,
    val id : String? = null
) : NavigatorResult

enum class OnlinePaymentNavigatorResultType(val successMessage: StringResource? = null) {
    SEND_RECEIPT(MR.strings.receipt_sent),
    REFUND(MR.strings.payment_refunded),
    CHARGED(MR.strings.payment_charged),
    PROCESS_NEW_PAYMENT(MR.strings.process_new_payment_successfully),
    DEFAULT
}

data class NavigatorResultString(
    val value: Boolean,
) : NavigatorResult

private val savedResult = mutableStateOf<NavigatorResult?>(
    null
)

fun Navigator.popWithResult(result: NavigatorResult) {
    savedResult.value = result
    pop()
}

fun Navigator.pushWithResult(result: NavigatorResult, screen: Screen) {
    savedResult.value = result
    val existingScreen = items.firstOrNull { it.key == screen.key }
    if (existingScreen == null) {
        push(screen)
    }
}


fun Navigator.pushX(screen: Screen) {
    val existingScreen = items.firstOrNull { it.key == screen.key }
    if (existingScreen == null) {
        push(screen)
    }
}

fun Navigator.replaceAllX(screen: Screen) {
    if (items.last().key != screen.key && items.last().uniqueScreenKey != screen.uniqueScreenKey) {
        replaceAll(screen)
    }
}

fun Navigator.popUntilWithResult(predicate: (Screen) -> Boolean, result: NavigatorResult) {
    val currentScreen = lastItem
    savedResult.value = result
    popUntil(predicate)
}

fun Navigator.clearResults() {
    savedResult.value = null
}

@Composable
fun getResult(): State<NavigatorResult?> {
    val result = savedResult
    val resultState = remember(result) {
        derivedStateOf {
            result.value
        }
    }
    return resultState
}