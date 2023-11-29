package com.dna.payments.kmm.di

import com.dna.payments.kmm.data.preferences.DefaultPreferences
import com.dna.payments.kmm.data.preferences.Preferences
import com.dna.payments.kmm.data.repository.DefaultAccessLevelRepository
import com.dna.payments.kmm.data.repository.DefaultAuthorizationRepository
import com.dna.payments.kmm.data.repository.DefaultResetPasswordRepository
import com.dna.payments.kmm.data.repository.DefaultStoresRepository
import com.dna.payments.kmm.data.use_case.ChangePasswordUseCaseImpl
import com.dna.payments.kmm.data.use_case.SendOtpInstructionsUseCaseImpl
import com.dna.payments.kmm.data.use_case.VerifyOtpCodeUseCaseImpl
import com.dna.payments.kmm.domain.interactors.use_cases.access_level.AccessLevelUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.authorization.AuthorizationUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.drawer.DrawerUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.pincode.PinUseCase
import com.dna.payments.kmm.domain.interactors.validation.ValidateCode
import com.dna.payments.kmm.domain.interactors.validation.ValidateEmail
import com.dna.payments.kmm.domain.interactors.validation.ValidatePassword
import com.dna.payments.kmm.domain.repository.AccessLevelRepository
import com.dna.payments.kmm.domain.repository.AuthorizationRepository
import com.dna.payments.kmm.domain.repository.ResetPasswordRepository
import com.dna.payments.kmm.domain.repository.StoresRepository
import com.dna.payments.kmm.domain.use_case.ChangePasswordUseCase
import com.dna.payments.kmm.domain.use_case.GetTerminalSettingsUseCase
import com.dna.payments.kmm.domain.use_case.SendOtpInstructionsUseCase
import com.dna.payments.kmm.domain.use_case.VerifyOtpCodeUseCase
import com.dna.payments.kmm.presentation.ui.features.drawer_navigation.DrawerNavigationViewModel
import com.dna.payments.kmm.presentation.ui.features.login.LoginViewModel
import com.dna.payments.kmm.presentation.ui.features.nav_auth.NavAuthViewModel
import com.dna.payments.kmm.presentation.ui.features.new_password.NewPasswordViewModel
import com.dna.payments.kmm.presentation.ui.features.payment_methods_detail.DetailPaymentMethodsViewModel
import com.dna.payments.kmm.presentation.ui.features.pincode.PinViewModel
import com.dna.payments.kmm.presentation.ui.features.restore_password.RestorePasswordViewModel
import com.dna.payments.kmm.presentation.ui.features.verification_code.VerificationCodeViewModel
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
    factoryOf(::LoginViewModel)
    factory { params -> PinViewModel(get(), get(), params.get(), params.get(), params.get(), params.get()) }
    factoryOf(::NavAuthViewModel)
    factoryOf(::RestorePasswordViewModel)
    factoryOf(::VerificationCodeViewModel)
    factoryOf(::NewPasswordViewModel)
    factoryOf(::DetailPaymentMethodsViewModel)
    factoryOf(::DrawerNavigationViewModel)
}

val useCasesModule: Module = module {
    factoryOf(::AuthorizationUseCase)
    factoryOf(::PinUseCase)
    singleOf(::AccessLevelUseCase)
    singleOf(::DrawerUseCase)

    //validation
    factoryOf(::ValidatePassword)
    factoryOf(::ValidateEmail)
    factoryOf(::ValidateCode)
    singleOf(::GetTerminalSettingsUseCase)
    factoryOf(::SendOtpInstructionsUseCaseImpl).bind(SendOtpInstructionsUseCase::class)
    factoryOf(::VerifyOtpCodeUseCaseImpl).bind(VerifyOtpCodeUseCase::class)
    factoryOf(::ChangePasswordUseCaseImpl).bind(ChangePasswordUseCase::class)
}

val repositoryModule = module {
    factoryOf(::DefaultAuthorizationRepository).bind(AuthorizationRepository::class)
    factoryOf(::DefaultResetPasswordRepository).bind(ResetPasswordRepository::class)
    factoryOf(::DefaultAccessLevelRepository).bind(AccessLevelRepository::class)
    factoryOf(::DefaultStoresRepository).bind(StoresRepository::class)
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
}

val sqlDelightModule = module {

}

val dispatcherModule = module {
    factory { Dispatchers.Default }
}

val mapperModule = module {

}

fun initKoin() = initKoin {}



