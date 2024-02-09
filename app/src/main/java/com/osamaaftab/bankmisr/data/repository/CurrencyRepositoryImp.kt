package com.osamaaftab.bankmisr.data.repository

import com.osamaaftab.bankmisr.data.ApiErrorHandle
import com.osamaaftab.bankmisr.data.repository.base.BaseRepositoryImp
import com.osamaaftab.bankmisr.data.source.remote.CurrencyRemoteDataSource
import com.osamaaftab.bankmisr.domain.model.LatestRatesResponseModel
import com.osamaaftab.bankmisr.domain.model.Resource
import com.osamaaftab.bankmisr.domain.model.SymbolResponseModel
import com.osamaaftab.bankmisr.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CurrencyRepositoryImp(
    private val currencyRemoteDataSource: CurrencyRemoteDataSource,
    errorHandle: ApiErrorHandle
) : CurrencyRepository, BaseRepositoryImp(errorHandle) {


    override suspend fun getCurrencySymbols(): Flow<Resource<SymbolResponseModel>> = flow {
        emit(currencyRemoteDataSource.getCurrencySymbolsAsync().awaitAndCatch())
    }

    override suspend fun getLatestRates(): Flow<Resource<LatestRatesResponseModel>> = flow {
        emit(currencyRemoteDataSource.getLatestRatesAsync().awaitAndCatch())
    }
}