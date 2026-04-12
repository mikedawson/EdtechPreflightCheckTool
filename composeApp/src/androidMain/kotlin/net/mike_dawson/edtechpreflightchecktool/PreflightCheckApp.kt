package net.mike_dawson.edtechpreflightchecktool

import android.app.Application
import net.mike_dawson.edtechpreflightchecktool.di.androidKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PreflightCheckApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PreflightCheckApp)
            modules(androidKoinModule)
        }

    }
}