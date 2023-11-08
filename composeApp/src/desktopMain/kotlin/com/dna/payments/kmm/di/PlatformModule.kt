package com.dna.payments.kmm.di

import com.dna.payments.kmm.data.cache.sqldelight.DatabaseDriverFactory
import com.dna.payments.kmm.utils.maps.geo.LocationTracker
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { DatabaseDriverFactory() }
    single { LocationTracker() }
    single {  }
}
