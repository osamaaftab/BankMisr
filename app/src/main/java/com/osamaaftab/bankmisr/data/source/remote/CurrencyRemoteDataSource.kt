package com.osamaaftab.bankmisr.data.source.remote

import com.osamaaftab.bankmisr.data.service.CurrencyServices
import com.osamaaftab.bankmisr.data.source.base.CurrencyDataSource
import com.osamaaftab.bankmisr.domain.model.LatestRatesResponseModel
import com.osamaaftab.bankmisr.domain.model.SymbolResponseModel
import kotlinx.coroutines.Deferred

class CurrencyRemoteDataSource(private val currencyServices: CurrencyServices) : CurrencyDataSource {

    override suspend fun getCurrencySymbolsAsync(): Deferred<SymbolResponseModel> {
        return currencyServices.getCurrencySymbolsAsync()
    }

    override suspend fun getLatestRatesAsync(): Deferred<LatestRatesResponseModel> {
        return currencyServices.getLatestRatesAsync()
    }
}