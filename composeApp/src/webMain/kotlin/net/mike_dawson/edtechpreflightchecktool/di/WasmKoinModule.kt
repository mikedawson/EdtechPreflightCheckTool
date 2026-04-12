package net.mike_dawson.edtechpreflightchecktool.di

import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val wasmKoinModule = module {
    viewModelOf(::PlanListViewModel)
}