package com.osamaaftab.bankmisr.domain.model

import com.osamaaftab.bankmisr.presentation.holder.CurrencyHolder

data class ConvertModel(
    val amount: Double,
    val fromCurrency: CurrencyHolder,
    val toCurrency: CurrencyHolder
)