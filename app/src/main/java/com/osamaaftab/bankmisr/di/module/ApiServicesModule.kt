package com.osamaaftab.bankmisr.di.module

import com.osamaaftab.bankmisr.data.service.CurrencyServices
import org.koin.dsl.module
import retrofit2.Retrofit

val ApiServicesModule = module {
    single { provideCurrencyService(get()) }
}

private fun provideCurrencyService(retrofit: Retrofit): CurrencyServices {
    return retrofit.create(CurrencyServices::class.java)
}

