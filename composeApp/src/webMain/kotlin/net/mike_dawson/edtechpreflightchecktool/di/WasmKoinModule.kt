package net.mike_dawson.edtechpreflightchecktool.di

import com.russhwolf.settings.Settings
import com.russhwolf.settings.StorageSettings
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanEditViewModel
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val wasmKoinModule = module {
    viewModelOf(::PlanListViewModel)
    viewModelOf(::PlanEditViewModel)

    single<Settings> {
        StorageSettings()
    }
}