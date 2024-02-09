package com.osamaaftab.bankmisr.domain.model

data class SymbolResponseModel(override val success: Boolean, val symbols: MutableMap<String, String>?,
                               override val error: ApiErrorModel?
) : Response