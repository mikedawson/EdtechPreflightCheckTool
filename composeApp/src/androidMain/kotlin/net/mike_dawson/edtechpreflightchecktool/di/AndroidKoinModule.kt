package net.mike_dawson.edtechpreflightchecktool.di

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import edtechpreflightchecktool.composeapp.generated.resources.Res
import kotlinx.serialization.json.Json
import net.mike_dawson.edtechpreflightchecktool.datalayer.datasource.CurrencyDataSource
import net.mike_dawson.edtechpreflightchecktool.datalayer.datasource.PlanDataSource
import net.mike_dawson.edtechpreflightchecktool.nav.NavResultReturner
import net.mike_dawson.edtechpreflightchecktool.nav.NavResultReturnerImpl
import net.mike_dawson.edtechpreflightchecktool.viewmodel.CostEditViewModel
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanDetailViewModel
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanEditViewModel
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

const val SHARED_PREF_SETTINGS_NAME = "shared_pref_settings"

val androidKoinModule = module {
    viewModelOf(::PlanListViewModel)
    viewModelOf(::PlanEditViewModel)
    viewModelOf(::PlanDetailViewModel)
    viewModelOf(::CostEditViewModel)

    single<Settings> {
        SharedPreferencesSettings(
            delegate = androidContext().getSharedPreferences(
                SHARED_PREF_SETTINGS_NAME,
                Context.MODE_PRIVATE
            )
        )
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