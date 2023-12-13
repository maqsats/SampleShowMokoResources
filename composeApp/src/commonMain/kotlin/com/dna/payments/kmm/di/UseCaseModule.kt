package com.dna.payments.kmm.di

import com.dna.payments.kmm.data.use_case.ChangePasswordUseCaseImpl
import com.dna.payments.kmm.data.use_case.SendOtpInstructionsUseCaseImpl
import com.dna.payments.kmm.data.use_case.VerifyOtpCodeUseCaseImpl
import com.dna.payments.kmm.domain.interactors.use_cases.access_level.AccessLevelUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.authorization.AuthorizationUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.DateHelper
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.GetDateRangeUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.drawer.DrawerUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.pincode.PinUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.profile.MerchantUseCase
import com.dna.payments.kmm.domain.interactors.validation.ValidateCode
import com.dna.payments.kmm.domain.interactors.validation.ValidateEmail
import com.dna.payments.kmm.domain.interactors.validation.ValidatePassword
import com.dna.payments.kmm.domain.use_case.ChangePasswordUseCase
import com.dna.payments.kmm.domain.use_case.GetDetailTerminalSettingsUseCase
import com.dna.payments.kmm.domain.use_case.GetDomainsUseCase
import com.dna.payments.kmm.domain.use_case.GetTerminalSettingsUseCase
import com.dna.payments.kmm.domain.use_case.SendOtpInstructionsUseCase
import com.dna.payments.kmm.domain.use_case.VerifyOtpCodeUseCase
import com.dna.payments.kmm.presentation.ui.features.team_management.TeamManagementByUserPageSource
import com.dna.payments.kmm.presentation.ui.features.team_management.TeamManagementInvitedPageSource
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
    singleOf(::TeamManagementByUserPageSource)
    singleOf(::TeamManagementInvitedPageSource)
    singleOf(::DateHelper)
    singleOf(::GetDateRangeUseCase)

    //validation
    factoryOf(::ValidatePassword)
    factoryOf(::ValidateEmail)
    factoryOf(::ValidateCode)
    factoryOf(::GetDomainsUseCase)
    factoryOf(::GetTerminalSettingsUseCase)
    factoryOf(::GetDetailTerminalSettingsUseCase)
    factoryOf(::SendOtpInstructionsUseCaseImpl).bind(SendOtpInstructionsUseCase::class)
    factoryOf(::VerifyOtpCodeUseCaseImpl).bind(VerifyOtpCodeUseCase::class)
    factoryOf(::ChangePasswordUseCaseImpl).bind(ChangePasswordUseCase::class)
}


