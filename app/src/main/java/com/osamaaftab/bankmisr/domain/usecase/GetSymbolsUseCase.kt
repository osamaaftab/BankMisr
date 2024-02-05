package com.osamaaftab.bankmisr.domain.usecase

import com.osamaaftab.bankmisr.domain.model.Resource
import com.osamaaftab.bankmisr.domain.model.SymbolsModel
import com.osamaaftab.bankmisr.domain.repository.CurrencyRepository
import com.osamaaftab.bankmisr.domain.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow

class GetSymbolsUseCase constructor(
    private val currencyRepository: CurrencyRepository
) : UseCase<SymbolsModel, Unit>() {

    override suspend fun run(params: Unit?): Flow<Resource<SymbolsModel>> {
        return currencyRepository.getCurrencySymbols()
    }
}