package com.dnapayments.mp.di

import com.dnapayments.mp.presentation.ui.features.drawer_navigation.DrawerViewModel
import com.dnapayments.mp.presentation.ui.features.login.LoginViewModel
import com.dnapayments.mp.presentation.ui.features.nav_auth.NavAuthViewModel
import com.dnapayments.mp.presentation.ui.features.new_password.NewPasswordViewModel
import com.dnapayments.mp.presentation.ui.features.new_payment_link.NewPaymentLinkViewModel
import com.dnapayments.mp.presentation.ui.features.online_payments.OnlinePaymentsViewModel
import com.dnapayments.mp.presentation.ui.features.online_payments.charge.OnlinePaymentChargeViewModel
import com.dnapayments.mp.presentation.ui.features.online_payments.detail.DetailOnlinePaymentViewModel
import com.dnapayments.mp.presentation.ui.features.online_payments.receipt.GetReceiptViewModel
import com.dnapayments.mp.presentation.ui.features.online_payments.refund.OnlinePaymentRefundViewModel
import com.dnapayments.mp.presentation.ui.features.overview_report.OverviewReportViewModel
import com.dnapayments.mp.presentation.ui.features.overview_report.widgets.approval_average_metrics.ApprovalAverageMetricsViewModel
import com.dnapayments.mp.presentation.ui.features.overview_report.widgets.chart.ChartViewModel
import com.dnapayments.mp.presentation.ui.features.overview_report.widgets.product_guide.ProductGuideViewModel
import com.dnapayments.mp.presentation.ui.features.overview_report.widgets.transactions.TransactionsViewModel
import com.dnapayments.mp.presentation.ui.features.payment_links.PaymentLinksViewModel
import com.dnapayments.mp.presentation.ui.features.payment_methods_add_domain.first_step.AddDomainFirstStepViewModel
import com.dnapayments.mp.presentation.ui.features.payment_methods_add_domain.third_step.AddDomainThirdStepViewModel
import com.dnapayments.mp.presentation.ui.features.payment_methods_detail.DetailPaymentMethodsViewModel
import com.dnapayments.mp.presentation.ui.features.pincode.PinViewModel
import com.dnapayments.mp.presentation.ui.features.pos_payments.PosPaymentsViewModel
import com.dnapayments.mp.presentation.ui.features.restore_password.RestorePasswordViewModel
import com.dnapayments.mp.presentation.ui.features.team_management.TeamManagementViewModel
import com.dnapayments.mp.presentation.ui.features.verification_code.VerificationCodeViewModel
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

    factory { params ->
        OnlinePaymentRefundViewModel(
            get(),
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
    factoryOf(::OnlinePaymentChargeViewModel)
}

