package com.osamaaftab.bankmisr.domain.repository

import com.osamaaftab.bankmisr.domain.model.LatestRatesModel
import com.osamaaftab.bankmisr.domain.model.Resource
import com.osamaaftab.bankmisr.domain.model.SymbolsModel
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun getCurrencySymbols(): Flow<Resource<SymbolsModel>>
    suspend fun getLatestRates(): Flow<Resource<LatestRatesModel>>
}