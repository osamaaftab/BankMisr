package com.osamaaftab.bankmisr.domain.repository

import com.osamaaftab.bankmisr.domain.model.LatestRatesResponseModel
import com.osamaaftab.bankmisr.domain.model.Resource
import com.osamaaftab.bankmisr.domain.model.SymbolResponseModel
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun getCurrencySymbols(): Flow<Resource<SymbolResponseModel>>
    suspend fun getLatestRates(): Flow<Resource<LatestRatesResponseModel>>
}