package com.osamaaftab.bankmisr.data.service

import com.osamaaftab.bankmisr.BuildConfig
import com.osamaaftab.bankmisr.domain.model.LatestRatesModel
import com.osamaaftab.bankmisr.domain.model.SymbolsModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyServices {

    @GET("symbols")
    fun getCurrencySymbolsAsync(@Query("access_key") key: String = BuildConfig.API_KEY)
            : Deferred<SymbolsModel>


    @GET("latest")
    fun getLatestRatesAsync(@Query("access_key") key: String = BuildConfig.API_KEY)
            : Deferred<LatestRatesModel>
}