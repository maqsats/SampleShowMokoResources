package daniel.avila.rnm.kmm.di

import daniel.avila.rnm.kmm.data.RemoteDataImp
import daniel.avila.rnm.kmm.data.cache.CacheDataImp
import daniel.avila.rnm.kmm.data.cache.sqldelight.SharedDatabase
import daniel.avila.rnm.kmm.data.model.mapper.ApiCharacterMapper
import daniel.avila.rnm.kmm.data.model.mapper.CityMapper
import daniel.avila.rnm.kmm.data.model.mapper.CurrencyMapper
import daniel.avila.rnm.kmm.data.model.mapper.ExchangeRateMapper
import daniel.avila.rnm.kmm.data.model.mapper.NationalBankMapper
import daniel.avila.rnm.kmm.data.preferences.DefaultPreferences
import daniel.avila.rnm.kmm.data.preferences.Preferences
import daniel.avila.rnm.kmm.data.repository.ICacheData
import daniel.avila.rnm.kmm.data.repository.IRemoteData
import daniel.avila.rnm.kmm.data.repository.city.DefaultCityRepository
import daniel.avila.rnm.kmm.data.repository.city.DefaultRemoteCityRepository
import daniel.avila.rnm.kmm.data.repository.currency.DefaultCurrencyRepository
import daniel.avila.rnm.kmm.data.repository.currency.DefaultRemoteCurrencyRepository
import daniel.avila.rnm.kmm.data.repository.exchange_rate.DefaultExchangeRateRepository
import daniel.avila.rnm.kmm.data.repository.exchange_rate.DefaultRemoteExchangeRateRepository
import daniel.avila.rnm.kmm.data.repository.national_bank.DefaultNationalBankRepository
import daniel.avila.rnm.kmm.data.repository.national_bank.DefaultRemoteNationalBankRepository
import daniel.avila.rnm.kmm.domain.IRepository
import daniel.avila.rnm.kmm.domain.interactors.GetCharacterUseCase
import daniel.avila.rnm.kmm.domain.interactors.GetCharactersFavoritesUseCase
import daniel.avila.rnm.kmm.domain.interactors.GetCharactersUseCase
import daniel.avila.rnm.kmm.domain.interactors.IsCharacterFavoriteUseCase
import daniel.avila.rnm.kmm.domain.interactors.SwitchCharacterFavoriteUseCase
import daniel.avila.rnm.kmm.domain.interactors.city.GetCityUseCase
import daniel.avila.rnm.kmm.domain.interactors.currency.GetCurrencyUseCase
import daniel.avila.rnm.kmm.domain.interactors.exchange_rate.GetExchangeRateUseCase
import daniel.avila.rnm.kmm.domain.interactors.exchanger.GetExchangerUseCase
import daniel.avila.rnm.kmm.domain.interactors.national_bank.GetNBCurrencyByRangeUseCase
import daniel.avila.rnm.kmm.domain.interactors.national_bank.GetNationalBankCurrencyUseCase
import daniel.avila.rnm.kmm.domain.repository.RepositoryImp
import daniel.avila.rnm.kmm.domain.repository.city.CityRepository
import daniel.avila.rnm.kmm.domain.repository.city.RemoteCityRepository
import daniel.avila.rnm.kmm.domain.repository.currency.CurrencyRepository
import daniel.avila.rnm.kmm.domain.repository.currency.RemoteCurrencyRepository
import daniel.avila.rnm.kmm.domain.repository.exchange_rate.ExchangeRateRepository
import daniel.avila.rnm.kmm.domain.repository.exchange_rate.RemoteExchangeRateRepository
import daniel.avila.rnm.kmm.domain.repository.national_bank.NationalBankRepository
import daniel.avila.rnm.kmm.domain.repository.national_bank.RemoteNationalBankRepository
import daniel.avila.rnm.kmm.presentation.ui.features.all_places.AllPlacesViewModel
import daniel.avila.rnm.kmm.presentation.ui.features.calculator.city.CityViewModel
import daniel.avila.rnm.kmm.presentation.ui.features.calculator.currency.CurrencyViewModel
import daniel.avila.rnm.kmm.presentation.ui.features.calculator.exchange_list_main.ExchangeRateViewModel
import daniel.avila.rnm.kmm.presentation.ui.features.character_detail.CharacterDetailViewModel
import daniel.avila.rnm.kmm.presentation.ui.features.characters.CharactersViewModel
import daniel.avila.rnm.kmm.presentation.ui.features.characters_favorites.CharactersFavoritesViewModel
import daniel.avila.rnm.kmm.presentation.ui.features.national_bank_currency.NBCurrencyViewModel
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
            preferencesModule,
            platformModule()
        )
    }

val viewModelModule = module {
    factory { CharactersViewModel(get()) }
    factory { CharactersFavoritesViewModel(get()) }
    factoryOf(::ExchangeRateViewModel)
    factoryOf(::CurrencyViewModel)
    factoryOf(::CityViewModel)
    factoryOf(::AllPlacesViewModel)
    factoryOf(::NBCurrencyViewModel)
    factory { params -> CharacterDetailViewModel(get(), get(), get(), params.get()) }
}

val useCasesModule: Module = module {
    factory { GetCharactersUseCase(get(), get()) }
    factory { GetCharactersFavoritesUseCase(get(), get()) }
    factory { GetCharacterUseCase(get(), get()) }
    factory { IsCharacterFavoriteUseCase(get(), get()) }
    factory { SwitchCharacterFavoriteUseCase(get(), get()) }
    factoryOf(::GetExchangeRateUseCase)
    factoryOf(::GetCurrencyUseCase)
    factoryOf(::GetNationalBankCurrencyUseCase)
    factoryOf(::GetNBCurrencyByRangeUseCase)
    factoryOf(::GetCityUseCase)
    factoryOf(::GetExchangerUseCase)
}

val repositoryModule = module {
    single<IRepository> { RepositoryImp(get(), get()) }
    single<ICacheData> { CacheDataImp(get()) }

    singleOf(::DefaultExchangeRateRepository).bind(ExchangeRateRepository::class)
    singleOf(::DefaultRemoteExchangeRateRepository).bind(RemoteExchangeRateRepository::class)

    singleOf(::DefaultCurrencyRepository).bind(CurrencyRepository::class)
    singleOf(::DefaultRemoteCurrencyRepository).bind(RemoteCurrencyRepository::class)

    singleOf(::DefaultCityRepository).bind(CityRepository::class)
    singleOf(::DefaultRemoteCityRepository).bind(RemoteCityRepository::class)

    singleOf(::DefaultNationalBankRepository).bind(NationalBankRepository::class)
    singleOf(::DefaultRemoteNationalBankRepository).bind(RemoteNationalBankRepository::class)

    single<IRemoteData> { RemoteDataImp(get(), get(), get()) }
}

val preferencesModule = module {
    singleOf(::DefaultPreferences).bind(Preferences::class)
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
    factoryOf(::CurrencyMapper)
    factoryOf(::CityMapper)
    factoryOf(::NationalBankMapper)
}

fun initKoin() = initKoin {}



