package com.osamaaftab.bankmisr.domain.model

data class LatestRatesModel(override val success: Boolean,val base : String, val rates: MutableMap<String, String>?) :
    ResultStatus