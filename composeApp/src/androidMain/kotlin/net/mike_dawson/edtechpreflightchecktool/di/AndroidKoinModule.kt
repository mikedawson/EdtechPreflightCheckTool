package net.mike_dawson.edtechpreflightchecktool.di

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanEditViewModel
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

const val SHARED_PREF_SETTINGS_NAME = "shared_pref_settings"

val androidKoinModule = module {
    viewModelOf(::PlanListViewModel)
    viewModelOf(::PlanEditViewModel)

    single<Settings> {
        SharedPreferencesSettings(
            delegate = androidContext().getSharedPreferences(
                SHARED_PREF_SETTINGS_NAME,
                Context.MODE_PRIVATE
            )
        )
    }

}