package com.dnapayments.mp.di

import com.dnapayments.mp.data.cache.sqldelight.DatabaseDriverFactory
import com.dnapayments.mp.utils.maps.geo.LocationTracker
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { DatabaseDriverFactory() }
    single { LocationTracker() }
    single {  }
}
