package net.mike_dawson.edtechpreflightchecktool.di

import com.russhwolf.settings.Settings
import com.russhwolf.settings.StorageSettings
import edtechpreflightchecktool.composeapp.generated.resources.Res
import kotlinx.serialization.json.Json
import net.mike_dawson.edtechpreflightchecktool.datalayer.datasource.CurrencyDataSource
import net.mike_dawson.edtechpreflightchecktool.datalayer.datasource.PlanDataSource
import net.mike_dawson.edtechpreflightchecktool.nav.NavResultReturner
import net.mike_dawson.edtechpreflightchecktool.nav.NavResultReturnerImpl
import net.mike_dawson.edtechpreflightchecktool.viewmodel.CostEditViewModel
import net.mike_dawson.edtechpreflightchecktool.viewmodel.InterventionEditViewModel
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanDetailViewModel
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanEditViewModel
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val wasmKoinModule = module {
    viewModelOf(::PlanListViewModel)
    viewModelOf(::PlanEditViewModel)
    viewModelOf(::PlanDetailViewModel)
    viewModelOf(::CostEditViewModel)
    viewModelOf(::InterventionEditViewModel)

    single<Settings> {
        StorageSettings()
    }

    single<Json> {
        Json {
            encodeDefaults = false
            ignoreUnknownKeys = true
        }
    }

    single<PlanDataSource> {
        PlanDataSource(
            settings = get(),
            json = get()
        )
    }

    single<CurrencyDataSource> {
        CurrencyDataSource(
            json = get(),
            currencyJsonProvider = {
                Res.readBytes("files/currencies.json").decodeToString()
            }
        )
    }

    single<NavResultReturner> {
        NavResultReturnerImpl()
    }
}