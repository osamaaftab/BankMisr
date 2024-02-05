package com.osamaaftab.bankmisr.di.module

import com.osamaaftab.bankmisr.data.ApiErrorHandle
import org.koin.dsl.module

val AppModule = module {
    single { provideApiError() }

}

fun provideApiError(): ApiErrorHandle {
    return ApiErrorHandle()
}