package com.osamaaftab.bankmisr.di.module


import com.osamaaftab.bankmisr.domain.repository.CurrencyRepository
import com.osamaaftab.bankmisr.domain.usecase.ConvertCurrencyUseCase
import com.osamaaftab.bankmisr.domain.usecase.GetSymbolsUseCase
import org.koin.dsl.module

val UseCaseModule = module {
    single { provideGetSymbolsUseCase(get()) }
    single { provideConvertCurrencyUseCase(get()) }
}

private fun provideGetSymbolsUseCase(
    propertyRepository: CurrencyRepository
): GetSymbolsUseCase {
    return GetSymbolsUseCase(propertyRepository)
}


private fun provideConvertCurrencyUseCase(
    currencyRepository: CurrencyRepository
): ConvertCurrencyUseCase {
    return ConvertCurrencyUseCase(currencyRepository)
}