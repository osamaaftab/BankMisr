package com.osamaaftab.bankmisr.domain.model

data class SymbolsModel(override val success: Boolean, val symbols: MutableMap<String, String>?) :
    ResultStatus