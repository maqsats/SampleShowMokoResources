package com.dnapayments.mp.di

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dispatcherModule = module {
    factory { Dispatchers.Default }
}