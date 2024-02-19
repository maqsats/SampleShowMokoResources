package com.dnapayments.mp.di

import com.dnapayments.mp.data.model.create_new_link.CreateNewLinkMapper
import com.dnapayments.mp.data.model.payment_links.PaymentLinkMapper
import com.dnapayments.mp.data.model.status_summary.StatusSummaryMapper
import com.dnapayments.mp.data.model.status_summary.pos_payments.PosPaymentsStatusSummaryMapper
import com.dnapayments.mp.data.model.team_management.TeamManagementMapper
import com.dnapayments.mp.data.model.transactions.TransactionPayLoadMapper
import com.dnapayments.mp.data.model.transactions.pos.PosTransactionMapper
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val mapperModule = module {
    singleOf(::TeamManagementMapper)
    singleOf(::PaymentLinkMapper)
    singleOf(::StatusSummaryMapper)
    singleOf(::TransactionPayLoadMapper)
    singleOf(::PosPaymentsStatusSummaryMapper)
    singleOf(::PosTransactionMapper)
    singleOf(::CreateNewLinkMapper)
}