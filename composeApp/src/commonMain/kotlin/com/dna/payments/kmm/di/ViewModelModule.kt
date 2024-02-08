package com.dna.payments.kmm.di

import com.dna.payments.kmm.presentation.ui.features.drawer_navigation.DrawerViewModel
import com.dna.payments.kmm.presentation.ui.features.login.LoginViewModel
import com.dna.payments.kmm.presentation.ui.features.nav_auth.NavAuthViewModel
import com.dna.payments.kmm.presentation.ui.features.new_password.NewPasswordViewModel
import com.dna.payments.kmm.presentation.ui.features.new_payment_link.NewPaymentLinkViewModel
import com.dna.payments.kmm.presentation.ui.features.online_payments.OnlinePaymentsViewModel
import com.dna.payments.kmm.presentation.ui.features.online_payments.detail.DetailOnlinePaymentViewModel
import com.dna.payments.kmm.presentation.ui.features.online_payments.receipt.GetReceiptViewModel
import com.dna.payments.kmm.presentation.ui.features.overview_report.OverviewReportViewModel
import com.dna.payments.kmm.presentation.ui.features.overview_report.widgets.approval_average_metrics.ApprovalAverageMetricsViewModel
import com.dna.payments.kmm.presentation.ui.features.overview_report.widgets.chart.ChartViewModel
import com.dna.payments.kmm.presentation.ui.features.overview_report.widgets.product_guide.ProductGuideViewModel
import com.dna.payments.kmm.presentation.ui.features.overview_report.widgets.transactions.TransactionsViewModel
import com.dna.payments.kmm.presentation.ui.features.payment_links.PaymentLinksViewModel
import com.dna.payments.kmm.presentation.ui.features.payment_methods_add_domain.first_step.AddDomainFirstStepViewModel
import com.dna.payments.kmm.presentation.ui.features.payment_methods_add_domain.third_step.AddDomainThirdStepViewModel
import com.dna.payments.kmm.presentation.ui.features.payment_methods_detail.DetailPaymentMethodsViewModel
import com.dna.payments.kmm.presentation.ui.features.pincode.PinViewModel
import com.dna.payments.kmm.presentation.ui.features.pos_payments.PosPaymentsViewModel
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
    factoryOf(::OverviewReportViewModel)
    factoryOf(::ProductGuideViewModel)
    factoryOf(::NewPaymentLinkViewModel)
    factoryOf(::DetailOnlinePaymentViewModel)

    factory { params ->
        OverviewReportViewModel(
            params.get(),
            get(),
            get(),
            get()
        )
    }
    factory { params ->
        ChartViewModel(
            get(),
            get(),
            get(),
            params.get(),
            params.get()
        )
    }
    factory { params ->
        TransactionsViewModel(
            get(),
            get(),
            get(),
            params.get()
        )
    }
    factory { params ->
        ApprovalAverageMetricsViewModel(
            get(),
            params.get()
        )
    }
    factoryOf(::PaymentLinksViewModel)
    factoryOf(::OnlinePaymentsViewModel)
    factoryOf(::PosPaymentsViewModel)
    factoryOf(::AddDomainFirstStepViewModel)
    factoryOf(::AddDomainThirdStepViewModel)
    factoryOf(::GetReceiptViewModel)
}

