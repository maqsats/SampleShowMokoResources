package com.dna.payments.kmm.di

import com.dna.payments.kmm.data.model.payment_links.PaymentLinkMapper
import com.dna.payments.kmm.data.model.status_summary.StatusSummaryMapper
import com.dna.payments.kmm.data.model.status_summary.pos_payments.PosPaymentsStatusSummaryMapper
import com.dna.payments.kmm.data.model.team_management.TeamManagementMapper
import com.dna.payments.kmm.data.model.transactions.TransactionPayLoadMapper
import com.dna.payments.kmm.data.model.transactions.pos.PosTransactionMapper
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val mapperModule = module {
    singleOf(::TeamManagementMapper)
    singleOf(::PaymentLinkMapper)
    singleOf(::StatusSummaryMapper)
    singleOf(::TransactionPayLoadMapper)
    singleOf(::PosPaymentsStatusSummaryMapper)
    singleOf(::PosTransactionMapper)
}