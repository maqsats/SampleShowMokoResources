package com.dna.payments.kmm.di

import com.dna.payments.kmm.data.RemoteDataImp
import com.dna.payments.kmm.data.cache.CacheDataImp
import com.dna.payments.kmm.data.cache.sqldelight.SharedDatabase
import com.dna.payments.kmm.data.model.mapper.ApiCharacterMapperOld
import com.dna.payments.kmm.data.preferences.DefaultPreferences
import com.dna.payments.kmm.data.preferences.Preferences
import com.dna.payments.kmm.data.repository.DefaultAuthorizationRepository
import com.dna.payments.kmm.data.repository.ICacheData
import com.dna.payments.kmm.data.repository.IRemoteData
import com.dna.payments.kmm.domain.IRepository
import com.dna.payments.kmm.domain.interactors.GetCharacterUseCase
import com.dna.payments.kmm.domain.interactors.GetCharactersFavoritesUseCase
import com.dna.payments.kmm.domain.interactors.GetCharactersUseCase
import com.dna.payments.kmm.domain.interactors.IsCharacterFavoriteUseCase
import com.dna.payments.kmm.domain.interactors.SwitchCharacterFavoriteUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.authorization.AuthorizationUseCase
import com.dna.payments.kmm.domain.interactors.validation.ValidateEmail
import com.dna.payments.kmm.domain.interactors.validation.ValidatePassword
import com.dna.payments.kmm.domain.repository.AuthorizationRepository
import com.dna.payments.kmm.domain.repository.RepositoryImp
import com.dna.payments.kmm.presentation.ui.features.character_detail.CharacterDetailViewModel
import com.dna.payments.kmm.presentation.ui.features.characters.CharactersViewModel
import com.dna.payments.kmm.presentation.ui.features.characters_favorites.CharactersFavoritesViewModel
import com.dna.payments.kmm.presentation.ui.features.login.LoginViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
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
    factoryOf(::LoginViewModel)
    factory { CharactersFavoritesViewModel(get()) }
    factory { params -> CharacterDetailViewModel(get(), get(), get(), params.get()) }
}

val useCasesModule: Module = module {
    factory { GetCharactersUseCase(get(), get()) }
    factory { GetCharactersFavoritesUseCase(get(), get()) }
    factory { GetCharacterUseCase(get(), get()) }
    factory { IsCharacterFavoriteUseCase(get(), get()) }
    factory { SwitchCharacterFavoriteUseCase(get(), get()) }
    factoryOf(::AuthorizationUseCase)

    //validation
    factoryOf(::ValidatePassword)
    factoryOf(::ValidateEmail)
}

val repositoryModule = module {
    single<IRepository> { RepositoryImp(get(), get()) }
    single<ICacheData> { CacheDataImp(get()) }
    single<IRemoteData> { RemoteDataImp(get(), get(), get()) }

    factoryOf(::DefaultAuthorizationRepository).bind(AuthorizationRepository::class)
}

val preferencesModule = module {
    singleOf(::DefaultPreferences).bind(Preferences::class)
}

val ktorModule = module {
    single {
        HttpClient {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("Ktor $message")
                    }
                }
                level = LogLevel.ALL
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 15000L
                socketTimeoutMillis = 15000L
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

    single { "https://rickandmortyapi.com" }
}

val sqlDelightModule = module {
    single { SharedDatabase(get()) }
}

val dispatcherModule = module {
    factory { Dispatchers.Default }
}

val mapperModule = module {
    factory { ApiCharacterMapperOld() }
}

fun initKoin() = initKoin {}



