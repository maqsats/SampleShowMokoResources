package com.dnapayments.mp.di

import com.dnapayments.mp.data.preferences.DefaultPreferences
import com.dnapayments.mp.data.preferences.Preferences
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val preferencesModule = module {
    singleOf(::DefaultPreferences).bind(Preferences::class)
}