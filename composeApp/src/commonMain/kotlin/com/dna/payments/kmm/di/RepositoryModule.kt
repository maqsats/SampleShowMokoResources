package com.dna.payments.kmm.di

import com.dna.payments.kmm.data.repository.DefaultAccessLevelRepository
import com.dna.payments.kmm.data.repository.DefaultAuthorizationRepository
import com.dna.payments.kmm.data.repository.DefaultCreateNewLinkRepository
import com.dna.payments.kmm.data.repository.DefaultDomainRepository
import com.dna.payments.kmm.data.repository.DefaultInfoRepository
import com.dna.payments.kmm.data.repository.DefaultPaymentLinksRepository
import com.dna.payments.kmm.data.repository.DefaultProfileRepository
import com.dna.payments.kmm.data.repository.DefaultResetPasswordRepository
import com.dna.payments.kmm.data.repository.DefaultSettingRepository
import com.dna.payments.kmm.data.repository.DefaultStoresRepository
import com.dna.payments.kmm.data.repository.DefaultTeamManagementRepository
import com.dna.payments.kmm.data.repository.DefaultTransactionRepository
import com.dna.payments.kmm.domain.repository.AccessLevelRepository
import com.dna.payments.kmm.domain.repository.AuthorizationRepository
import com.dna.payments.kmm.domain.repository.CreateNewLinkRepository
import com.dna.payments.kmm.domain.repository.DomainsRepository
import com.dna.payments.kmm.domain.repository.InfoRepository
import com.dna.payments.kmm.domain.repository.PaymentLinksRepository
import com.dna.payments.kmm.domain.repository.ProfileRepository
import com.dna.payments.kmm.domain.repository.ResetPasswordRepository
import com.dna.payments.kmm.domain.repository.SettingRepository
import com.dna.payments.kmm.domain.repository.StoresRepository
import com.dna.payments.kmm.domain.repository.TeamManagementRepository
import com.dna.payments.kmm.domain.repository.TransactionRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    factoryOf(::DefaultAuthorizationRepository).bind(AuthorizationRepository::class)
    factoryOf(::DefaultResetPasswordRepository).bind(ResetPasswordRepository::class)
    factoryOf(::DefaultAccessLevelRepository).bind(AccessLevelRepository::class)
    factoryOf(::DefaultProfileRepository).bind(ProfileRepository::class)
    factoryOf(::DefaultStoresRepository).bind(StoresRepository::class)
    factoryOf(::DefaultInfoRepository).bind(InfoRepository::class)
    factoryOf(::DefaultTeamManagementRepository).bind(TeamManagementRepository::class)
    factoryOf(::DefaultPaymentLinksRepository).bind(PaymentLinksRepository::class)
    factoryOf(::DefaultDomainRepository).bind(DomainsRepository::class)
    factoryOf(::DefaultTransactionRepository).bind(TransactionRepository::class)
    factoryOf(::DefaultCreateNewLinkRepository).bind(CreateNewLinkRepository::class)
    factoryOf(::DefaultSettingRepository).bind(SettingRepository::class)
}

