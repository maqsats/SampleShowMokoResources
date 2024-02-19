package com.dnapayments.mp.utils.firebase

import cocoapods.FirebaseAnalytics.FIRAnalytics
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
actual fun logEvent(
    name: String,
    parameters: Map<String, Any?>
) {
    FIRAnalytics.logEventWithName(
        name = name.take(40),
        parameters = parameters.mapValues { (_, value) ->
            when (value) {
                is String -> value.take(100)
                is Int -> value
                is Long -> value
                is Double -> value
                is Boolean -> value
                else -> value.toString().take(100)
            }
        }
    )
}