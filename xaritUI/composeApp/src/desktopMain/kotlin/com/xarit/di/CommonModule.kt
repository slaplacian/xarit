package com.xarit.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import com.xarit.presentation.screens.main.MainViewModel

val commonModule = module {
    factoryOf(::MainViewModel)
}