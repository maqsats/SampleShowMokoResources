package com.dnapayments.mp.di

import com.dnapayments.mp.data.repository.DefaultAccessLevelRepository
import com.dnapayments.mp.data.repository.DefaultAuthorizationRepository
import com.dnapayments.mp.data.repository.DefaultCreateNewLinkRepository
import com.dnapayments.mp.data.repository.DefaultDomainRepository
import com.dnapayments.mp.data.repository.DefaultInfoRepository
import com.dnapayments.mp.data.repository.DefaultOnlinePaymentOperationRepository
import com.dnapayments.mp.data.repository.DefaultPaymentLinksRepository
import com.dnapayments.mp.data.repository.DefaultProfileRepository
import com.dnapayments.mp.data.repository.DefaultResetPasswordRepository
import com.dnapayments.mp.data.repository.DefaultSettingRepository
import com.dnapayments.mp.data.repository.DefaultStoresRepository
import com.dnapayments.mp.data.repository.DefaultTeamManagementRepository
import com.dnapayments.mp.data.repository.DefaultTransactionRepository
import com.dnapayments.mp.domain.repository.AccessLevelRepository
import com.dnapayments.mp.domain.repository.AuthorizationRepository
import com.dnapayments.mp.domain.repository.CreateNewLinkRepository
import com.dnapayments.mp.domain.repository.DomainsRepository
import com.dnapayments.mp.domain.repository.InfoRepository
import com.dnapayments.mp.domain.repository.OnlinePaymentOperationRepository
import com.dnapayments.mp.domain.repository.PaymentLinksRepository
import com.dnapayments.mp.domain.repository.ProfileRepository
import com.dnapayments.mp.domain.repository.ResetPasswordRepository
import com.dnapayments.mp.domain.repository.SettingRepository
import com.dnapayments.mp.domain.repository.StoresRepository
import com.dnapayments.mp.domain.repository.TeamManagementRepository
import com.dnapayments.mp.domain.repository.TransactionRepository
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
    factoryOf(::DefaultOnlinePaymentOperationRepository).bind(OnlinePaymentOperationRepository::class)
}

