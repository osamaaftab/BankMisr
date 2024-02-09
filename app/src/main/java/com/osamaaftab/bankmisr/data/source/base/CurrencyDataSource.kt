package com.osamaaftab.bankmisr.data.source.base

import com.osamaaftab.bankmisr.domain.model.LatestRatesResponseModel
import com.osamaaftab.bankmisr.domain.model.SymbolResponseModel
import kotlinx.coroutines.Deferred

interface CurrencyDataSource {
    suspend fun getCurrencySymbolsAsync(): Deferred<SymbolResponseModel>
    suspend fun getLatestRatesAsync(): Deferred<LatestRatesResponseModel>
}