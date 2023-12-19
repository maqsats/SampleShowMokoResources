package com.dna.payments.kmm.di

import com.dna.payments.kmm.presentation.ui.features.drawer_navigation.DrawerViewModel
import com.dna.payments.kmm.presentation.ui.features.login.LoginViewModel
import com.dna.payments.kmm.presentation.ui.features.nav_auth.NavAuthViewModel
import com.dna.payments.kmm.presentation.ui.features.new_password.NewPasswordViewModel
import com.dna.payments.kmm.presentation.ui.features.overview.OverviewViewModel
import com.dna.payments.kmm.presentation.ui.features.payment_links.PaymentLinksViewModel
import com.dna.payments.kmm.presentation.ui.features.payment_methods_detail.DetailPaymentMethodsViewModel
import com.dna.payments.kmm.presentation.ui.features.pincode.PinViewModel
import com.dna.payments.kmm.presentation.ui.features.restore_password.RestorePasswordViewModel
import com.dna.payments.kmm.presentation.ui.features.team_management.TeamManagementViewModel
import com.dna.payments.kmm.presentation.ui.features.verification_code.VerificationCodeViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val viewModelModule = module {
    factoryOf(::LoginViewModel)
    factory { params ->
        PinViewModel(
            get(),
            get(),
            params.get(),
            params.get(),
            params.get(),
            params.get()
        )
    }
    factoryOf(::NavAuthViewModel)
    factoryOf(::TeamManagementViewModel)
    factoryOf(::RestorePasswordViewModel)
    factoryOf(::VerificationCodeViewModel)
    factoryOf(::NewPasswordViewModel)
    factoryOf(::DrawerViewModel)
    factoryOf(::DetailPaymentMethodsViewModel)
    factoryOf(::OverviewViewModel)
    factoryOf(::PaymentLinksViewModel)
}

