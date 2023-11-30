package com.dna.payments.kmm.di

import com.dna.payments.kmm.data.repository.DefaultAccessLevelRepository
import com.dna.payments.kmm.data.repository.DefaultAuthorizationRepository
import com.dna.payments.kmm.data.repository.DefaultProfileRepository
import com.dna.payments.kmm.data.repository.DefaultResetPasswordRepository
import com.dna.payments.kmm.data.repository.DefaultStoresRepository
import com.dna.payments.kmm.domain.repository.AccessLevelRepository
import com.dna.payments.kmm.domain.repository.AuthorizationRepository
import com.dna.payments.kmm.domain.repository.ProfileRepository
import com.dna.payments.kmm.domain.repository.ResetPasswordRepository
import com.dna.payments.kmm.domain.repository.StoresRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    factoryOf(::DefaultAuthorizationRepository).bind(AuthorizationRepository::class)
    factoryOf(::DefaultResetPasswordRepository).bind(ResetPasswordRepository::class)
    factoryOf(::DefaultAccessLevelRepository).bind(AccessLevelRepository::class)
    factoryOf(::DefaultProfileRepository).bind(ProfileRepository::class)
    factoryOf(::DefaultStoresRepository).bind(StoresRepository::class)
}

