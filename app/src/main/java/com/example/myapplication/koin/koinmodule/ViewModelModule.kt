package com.example.myapplication.koin.koinmodule

import com.example.myapplication.ui.MainCalculatorActivityVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class ViewModelModule {
    companion object {
        val modules = module(override = true) {
            viewModel { MainCalculatorActivityVM(get(), get(), get()) }
        }
    }
}