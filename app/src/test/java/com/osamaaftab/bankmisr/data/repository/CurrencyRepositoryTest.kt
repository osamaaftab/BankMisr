package com.osamaaftab.bankmisr.data.repository

import com.osamaaftab.bankmisr.data.ApiErrorHandle
import com.osamaaftab.bankmisr.data.source.remote.CurrencyRemoteDataSource
import com.osamaaftab.bankmisr.domain.model.LatestRatesResponseModel
import com.osamaaftab.bankmisr.domain.model.SymbolResponseModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.junit.Test


class CurrencyRepositoryTest {
    private val currencyRemoteDataSource: CurrencyRemoteDataSource = mockk()
    private val apiErrorHandle: ApiErrorHandle = mockk()

    private val currencyRepository = CurrencyRepositoryImp(currencyRemoteDataSource, apiErrorHandle)

    @Test
    fun `getCurrencySymbols should emit success`() = runBlocking {
        // Arrange
        val symbolResponseModel =
            SymbolResponseModel(true, mutableMapOf(/* mock your symbols data */), null)
        coEvery { currencyRemoteDataSource.getCurrencySymbolsAsync() } coAnswers { CompletableDeferred( symbolResponseModel) }

        // Act & Assert
        currencyRepository.getCurrencySymbols().collect {
            assertEquals(symbolResponseModel, it.data)
        }

        // Verify that the coroutine function was called
        coVerify { currencyRemoteDataSource.getCurrencySymbolsAsync() }
    }

    @Test
    fun `getLatestRates should emit success`() = runBlocking {
        // Arrange
        val latestRatesResponseModel =
            LatestRatesResponseModel(true, "", mutableMapOf(/* mock your data */), null)
        coEvery { currencyRemoteDataSource.getLatestRatesAsync() } coAnswers {
            CompletableDeferred(
                latestRatesResponseModel
            )
        }

        // Act & Assert
        currencyRepository.getLatestRates().collect {
            assertEquals(latestRatesResponseModel, it.data)
        }

        // Verify that the coroutine function was called
        coVerify { currencyRemoteDataSource.getLatestRatesAsync() }
    }
}
