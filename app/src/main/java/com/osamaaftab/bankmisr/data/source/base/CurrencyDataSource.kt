package com.osamaaftab.bankmisr.data.source.base

import com.osamaaftab.bankmisr.domain.model.LatestRatesModel
import com.osamaaftab.bankmisr.domain.model.SymbolsModel
import kotlinx.coroutines.Deferred

interface CurrencyDataSource {
    suspend fun getCurrencySymbolsAsync(): Deferred<SymbolsModel>
    suspend fun getLatestRatesAsync(): Deferred<LatestRatesModel>
}