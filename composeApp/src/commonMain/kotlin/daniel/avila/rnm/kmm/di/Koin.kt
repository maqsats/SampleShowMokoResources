package daniel.avila.rnm.kmm.di

import daniel.avila.rnm.kmm.data.RemoteDataImp
import daniel.avila.rnm.kmm.data.cache.CacheDataImp
import daniel.avila.rnm.kmm.data.cache.sqldelight.SharedDatabase
import daniel.avila.rnm.kmm.data.model.mapper.ApiCharacterMapper
import daniel.avila.rnm.kmm.data.model.mapper.ExchangeRateMapper
import daniel.avila.rnm.kmm.data.repository.DefaultExchangeRateRepository
import daniel.avila.rnm.kmm.data.repository.DefaultRemoteExchangeRateRepository
import daniel.avila.rnm.kmm.data.repository.ICacheData
import daniel.avila.rnm.kmm.data.repository.IRemoteData
import daniel.avila.rnm.kmm.domain.IRepository
import daniel.avila.rnm.kmm.domain.interactors.GetCharacterUseCase
import daniel.avila.rnm.kmm.domain.interactors.GetCharactersFavoritesUseCase
import daniel.avila.rnm.kmm.domain.interactors.GetCharactersUseCase
import daniel.avila.rnm.kmm.domain.interactors.GetExchangeRateUseCase
import daniel.avila.rnm.kmm.domain.interactors.IsCharacterFavoriteUseCase
import daniel.avila.rnm.kmm.domain.interactors.SwitchCharacterFavoriteUseCase
import daniel.avila.rnm.kmm.domain.repository.ExchangeRateRepository
import daniel.avila.rnm.kmm.domain.repository.RemoteExchangeRateRepository
import daniel.avila.rnm.kmm.domain.repository.RepositoryImp
import daniel.avila.rnm.kmm.presentation.ui.features.character_detail.CharacterDetailViewModel
import daniel.avila.rnm.kmm.presentation.ui.features.characters.CharactersViewModel
import daniel.avila.rnm.kmm.presentation.ui.features.characters_favorites.CharactersFavoritesViewModel
import daniel.avila.rnm.kmm.presentation.ui.features.exchange_places.TrackerViewModel
import daniel.avila.rnm.kmm.presentation.ui.features.main.exchange_list_main.ExchangeRateViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            viewModelModule,
            useCasesModule,
            repositoryModule,
            ktorModule,
            sqlDelightModule,
            mapperModule,
            dispatcherModule,
            platformModule()
        )
    }

val viewModelModule = module {
    factory { CharactersViewModel(get()) }
    factory { CharactersFavoritesViewModel(get()) }
    factory { TrackerViewModel(get()) }
    factoryOf(::ExchangeRateViewModel)
    factory { params -> CharacterDetailViewModel(get(), get(), get(), params.get()) }
}

val useCasesModule: Module = module {
    factory { GetCharactersUseCase(get(), get()) }
    factory { GetCharactersFavoritesUseCase(get(), get()) }
    factory { GetCharacterUseCase(get(), get()) }
    factory { IsCharacterFavoriteUseCase(get(), get()) }
    factory { SwitchCharacterFavoriteUseCase(get(), get()) }
    factoryOf(::GetExchangeRateUseCase)
}

val repositoryModule = module {
    single<IRepository> { RepositoryImp(get(), get()) }
    single<ICacheData> { CacheDataImp(get()) }
    singleOf(::DefaultRemoteExchangeRateRepository).bind(RemoteExchangeRateRepository::class)
    singleOf(::DefaultExchangeRateRepository).bind(ExchangeRateRepository::class)
    single<IRemoteData> { RemoteDataImp(get(), get(), get()) }
}

val ktorModule = module {
    single {
        HttpClient {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
        }
    }
}

val sqlDelightModule = module {
    single { SharedDatabase(get()) }
}

val dispatcherModule = module {
    factory { Dispatchers.Default }
}

val mapperModule = module {
    factory { ApiCharacterMapper() }
    factoryOf(::ExchangeRateMapper)
}

fun initKoin() = initKoin {}



