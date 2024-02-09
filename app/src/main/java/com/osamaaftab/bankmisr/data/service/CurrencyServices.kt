package com.osamaaftab.bankmisr.data.service

import com.osamaaftab.bankmisr.BuildConfig
import com.osamaaftab.bankmisr.domain.model.LatestRatesResponseModel
import com.osamaaftab.bankmisr.domain.model.SymbolResponseModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyServices {

    @GET("symbols")
    fun getCurrencySymbolsAsync(@Query("access_key") key: String = BuildConfig.API_KEY)
            : Deferred<SymbolResponseModel>


    @GET("latest")
    fun getLatestRatesAsync(@Query("access_key") key: String = BuildConfig.API_KEY)
            : Deferred<LatestRatesResponseModel>
}