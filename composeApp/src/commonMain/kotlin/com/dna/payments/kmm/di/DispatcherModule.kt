package com.dna.payments.kmm.di

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dispatcherModule = module {
    factory { Dispatchers.Default }
}