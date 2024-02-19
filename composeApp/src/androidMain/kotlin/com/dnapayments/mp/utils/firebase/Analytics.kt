package com.dnapayments.mp.utils.firebase

import android.os.Bundle
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

actual fun logEvent(
    name: String,
    parameters: Map<String, Any?>
) {
    Firebase.analytics.logEvent(name.take(40), Bundle().apply {
        parameters.forEach { (key, value) ->
            when (value) {
                is String -> putString(key, value.take(40))
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Double -> putDouble(key, value)
                is Boolean -> putBoolean(key, value)
            }
        }
    })
}