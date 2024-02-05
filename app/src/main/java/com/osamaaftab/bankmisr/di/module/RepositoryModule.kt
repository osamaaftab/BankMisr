package com.osamaaftab.bankmisr.di.module

import com.osamaaftab.bankmisr.data.ApiErrorHandle
import com.osamaaftab.bankmisr.data.repository.CurrencyRepositoryImp
import com.osamaaftab.bankmisr.data.source.remote.CurrencyRemoteDataSource
import com.osamaaftab.bankmisr.domain.repository.CurrencyRepository
import org.koin.dsl.module

val RepositoryModule = module {
    single { providePropertyRepository(get(), get()) }

}

fun providePropertyRepository(
    propertyRemoteDataSource: CurrencyRemoteDataSource,
    errorHandle: ApiErrorHandle
): CurrencyRepository {
    return CurrencyRepositoryImp(propertyRemoteDataSource, errorHandle)
}

