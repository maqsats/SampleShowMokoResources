package com.dna.payments.kmm.di

import com.dna.payments.kmm.data.model.team_management.TeamManagementMapper
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val mapperModule = module {
    singleOf(::TeamManagementMapper)
}