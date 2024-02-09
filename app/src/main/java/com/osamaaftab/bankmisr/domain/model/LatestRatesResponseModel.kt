package com.osamaaftab.bankmisr.domain.model

data class LatestRatesResponseModel(
    override val success: Boolean, val base: String, val rates: MutableMap<String, String>?,
    override val error: ApiErrorModel?
) : Response