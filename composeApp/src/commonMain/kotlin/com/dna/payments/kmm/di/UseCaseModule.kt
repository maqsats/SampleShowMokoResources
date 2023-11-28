package com.dna.payments.kmm.di

import com.dna.payments.kmm.data.preferences.DefaultPreferences
import com.dna.payments.kmm.data.preferences.Preferences
import com.dna.payments.kmm.data.repository.ChangePasswordUseCaseImpl
import com.dna.payments.kmm.data.repository.DefaultAccessLevelRepository
import com.dna.payments.kmm.data.repository.DefaultAuthorizationRepository
import com.dna.payments.kmm.data.repository.DefaultResetPasswordRepository
import com.dna.payments.kmm.data.repository.SendOtpInstructionsUseCaseImpl
import com.dna.payments.kmm.data.repository.VerifyOtpCodeUseCaseImpl
import com.dna.payments.kmm.domain.interactors.use_cases.access_level.AccessLevelUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.authorization.AuthorizationUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.drawer.DrawerUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.pincode.PinUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.profile.MerchantUseCase
import com.dna.payments.kmm.domain.interactors.validation.ValidateCode
import com.dna.payments.kmm.domain.interactors.validation.ValidateEmail
import com.dna.payments.kmm.domain.interactors.validation.ValidatePassword
import com.dna.payments.kmm.domain.repository.AccessLevelRepository
import com.dna.payments.kmm.domain.repository.AuthorizationRepository
import com.dna.payments.kmm.domain.repository.ChangePasswordUseCase
import com.dna.payments.kmm.domain.repository.ResetPasswordRepository
import com.dna.payments.kmm.domain.repository.SendOtpInstructionsUseCase
import com.dna.payments.kmm.domain.repository.VerifyOtpCodeUseCase
import com.dna.payments.kmm.presentation.ui.features.drawer_navigation.DrawerNavigationViewModel
import com.dna.payments.kmm.presentation.ui.features.login.LoginViewModel
import com.dna.payments.kmm.presentation.ui.features.nav_auth.NavAuthViewModel
import com.dna.payments.kmm.presentation.ui.features.new_password.NewPasswordViewModel
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


val useCasesModule: Module = module {
    factoryOf(::AuthorizationUseCase)
    factoryOf(::PinUseCase)
    singleOf(::AccessLevelUseCase)
    singleOf(::DrawerUseCase)
    singleOf(::MerchantUseCase)

    //validation
    factoryOf(::ValidatePassword)
    factoryOf(::ValidateEmail)
    factoryOf(::ValidateCode)
    factoryOf(::SendOtpInstructionsUseCaseImpl).bind(SendOtpInstructionsUseCase::class)
    factoryOf(::VerifyOtpCodeUseCaseImpl).bind(VerifyOtpCodeUseCase::class)
    factoryOf(::ChangePasswordUseCaseImpl).bind(ChangePasswordUseCase::class)
}


