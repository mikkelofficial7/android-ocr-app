package com.example.myapplication.koin

import com.example.myapplication.koin.koinmodule.FileModule
import com.example.myapplication.koin.koinmodule.RoomModule
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val MainAppModule = module(override = true) {
    single { FileModule.provideFile(androidContext(), get()) }
    single { FileModule.provideFileSecretKey() }
    single { RoomModule.provideRoom(androidContext()) }
}