package com.dna.payments.kmm.utils.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey


interface NavigatorResult

data class NavigatorResultString(
    val value: Boolean
) : NavigatorResult

private val results = mutableStateMapOf<String, NavigatorResult>()

fun Navigator.popWithResult(key: String, result: NavigatorResult) {
    results[key] = result
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
    results[currentScreen.key] = result
    popUntil(predicate)
}

fun Navigator.clearResults() {
    results.clear()
}

@Composable
fun Navigator.getResult(screenKey: String): State<NavigatorResult?> {
    val result = results[screenKey]
    val resultState = remember(screenKey, result) {
        derivedStateOf {
            results.remove(screenKey)
            result
        }
    }
    return resultState
}