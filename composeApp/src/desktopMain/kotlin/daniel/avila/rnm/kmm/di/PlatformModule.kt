package daniel.avila.rnm.kmm.di

import daniel.avila.rnm.kmm.data.cache.sqldelight.DatabaseDriverFactory
import daniel.avila.rnm.kmm.utils.maps.geo.LocationTracker
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { DatabaseDriverFactory() }
    single { LocationTracker() }
    single {  }
}
