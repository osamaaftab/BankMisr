package com.osamaaftab.bankmisr.domain.usecase

import com.osamaaftab.bankmisr.common.Util.roundDecimalToOneDigit
import com.osamaaftab.bankmisr.domain.model.ConvertModel
import com.osamaaftab.bankmisr.domain.model.ErrorModel
import com.osamaaftab.bankmisr.domain.model.Resource
import com.osamaaftab.bankmisr.domain.repository.CurrencyRepository
import com.osamaaftab.bankmisr.domain.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ConvertCurrencyUseCase constructor(
    private val currencyRepository: CurrencyRepository
) : UseCase<String, ConvertModel>() {


    override suspend fun run(params: ConvertModel?): Flow<Resource<String>> = flow {
        if (params == null) {
            throw IllegalArgumentException("Param must not be null")
        }

        val latestRates = currencyRepository.getLatestRates()

        latestRates.collect {
            if (it.errorModel != null) {
                emit(Resource.Error(null, it.errorModel))
            } else {
                val getToRates = it.data?.rates?.get(params.toCurrency.code)?.toDouble()
                val getFromRates = it.data?.rates?.get(params.fromCurrency.code)?.toDouble()

                if (getFromRates == null || getToRates == null) {
                    emit(
                        Resource.Error(
                            null, ErrorModel(
                                "Error occurred during conversion",
                                ErrorModel.ErrorStatus.NOT_DEFINED
                            )
                        )
                    )
                } else {
                    val convertedValue =
                        ((getToRates / getFromRates) * params.amount).roundDecimalToOneDigit()
                    emit(Resource.Success(convertedValue))
                }
            }
        }
    }
}