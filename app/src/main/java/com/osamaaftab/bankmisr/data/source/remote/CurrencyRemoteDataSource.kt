package com.osamaaftab.bankmisr.data.source.remote

import com.osamaaftab.bankmisr.data.service.CurrencyServices
import com.osamaaftab.bankmisr.data.source.base.CurrencyDataSource
import com.osamaaftab.bankmisr.domain.model.LatestRatesModel
import com.osamaaftab.bankmisr.domain.model.SymbolsModel
import kotlinx.coroutines.Deferred

class CurrencyRemoteDataSource(private val currencyServices: CurrencyServices) : CurrencyDataSource {

    override suspend fun getCurrencySymbolsAsync(): Deferred<SymbolsModel> {
        return currencyServices.getCurrencySymbolsAsync()
    }

    override suspend fun getLatestRatesAsync(): Deferred<LatestRatesModel> {
        return currencyServices.getLatestRatesAsync()
    }
}