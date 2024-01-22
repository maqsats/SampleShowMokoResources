package com.dna.payments.kmm.utils.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey


interface NavigatorResult

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