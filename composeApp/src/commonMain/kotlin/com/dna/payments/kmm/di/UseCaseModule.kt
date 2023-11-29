package com.dna.payments.kmm.di

import com.dna.payments.kmm.data.repository.ChangePasswordUseCaseImpl
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
import com.dna.payments.kmm.domain.repository.ChangePasswordUseCase
import com.dna.payments.kmm.domain.repository.SendOtpInstructionsUseCase
import com.dna.payments.kmm.domain.repository.VerifyOtpCodeUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
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


