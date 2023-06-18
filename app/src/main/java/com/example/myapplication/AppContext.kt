package com.example.myapplication

import android.app.Application
import com.example.myapplication.koin.MainAppModule
import com.example.myapplication.koin.koinmodule.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AppContext : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@AppContext)
            modules(MainAppModule, ViewModelModule.modules)
        }
    }

}