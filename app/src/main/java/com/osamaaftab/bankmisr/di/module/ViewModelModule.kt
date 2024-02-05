package com.osamaaftab.bankmisr.di.module

import com.osamaaftab.bankmisr.presentation.viewmodel.CurrencyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { CurrencyViewModel(get(),get()) }
}