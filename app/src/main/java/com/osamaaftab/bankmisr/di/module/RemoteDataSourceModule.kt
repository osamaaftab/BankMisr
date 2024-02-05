package com.osamaaftab.bankmisr.di.module

import com.osamaaftab.bankmisr.data.service.CurrencyServices
import com.osamaaftab.bankmisr.data.source.remote.CurrencyRemoteDataSource
import org.koin.dsl.module

val RemoteDataSourceModule = module {
    single { provideCurrencyPropertyDataSource(get()) }
}

fun provideCurrencyPropertyDataSource(
    propertyServices: CurrencyServices
): CurrencyRemoteDataSource {
    return CurrencyRemoteDataSource(propertyServices)
}