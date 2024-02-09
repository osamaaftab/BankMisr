package com.osamaaftab.bankmisr.domain

import com.osamaaftab.bankmisr.domain.model.ConvertModel
import com.osamaaftab.bankmisr.domain.model.ErrorModel
import com.osamaaftab.bankmisr.domain.model.LatestRatesResponseModel
import com.osamaaftab.bankmisr.domain.model.Resource
import com.osamaaftab.bankmisr.domain.repository.CurrencyRepository
import com.osamaaftab.bankmisr.domain.usecase.ConvertCurrencyUseCase
import com.osamaaftab.bankmisr.presentation.holder.CurrencyHolder
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.test.assertFailsWith

class ConvertCurrencyUseCaseTest {

    private val currencyRepository: CurrencyRepository = mockk()
    private val convertCurrencyUseCase = ConvertCurrencyUseCase(currencyRepository)

    @Test
    fun `convertCurrency should emit success`() = runBlocking {
        // Arrange
        val convertModel = ConvertModel(1.0, CurrencyHolder("USD","US dollar"),CurrencyHolder("PKR","Pakistani Rupee"))
        val latestRatesResponseModel = LatestRatesResponseModel(true, "EUR",  mutableMapOf("USD" to "1.0","PKR" to "277"),null)
        coEvery { currencyRepository.getLatestRates() } returns flowOf(Resource.Success(latestRatesResponseModel))

        // Act
        val result: Flow<Resource<String>> = convertCurrencyUseCase.run(convertModel)

        // Assert
        assertEquals(Resource.Success("277").data, result.single().data)
    }

    @Test
    fun `run should throw IllegalArgumentException for null params`()  {
        // Arrange
        val params: ConvertModel? = null
        val latestRatesResponseModel = mockk<LatestRatesResponseModel>(relaxed = true)

        // Mock behavior of currencyRepository.getLatestRates()
        coEvery { currencyRepository.getLatestRates() } returns flowOf(Resource.Success(latestRatesResponseModel))

        // Act & Assert
        assertFailsWith<IllegalArgumentException> {
            runBlocking{
            convertCurrencyUseCase.run(params).collect()
            }
        }

        // Verify that currencyRepository.getLatestRates() was not called
        coVerify(exactly = 0) { currencyRepository.getLatestRates() }
    }

    @Test
    fun `convertCurrency should emit error if getFromRates or getToRates is null`() = runBlocking {
        // Arrange
        val convertModel = ConvertModel(1.0, CurrencyHolder("MYR","MY ringgit"),CurrencyHolder("SAR","Saudi Riyal"))
        val latestRatesResponseModel = LatestRatesResponseModel(true, "EUR", mutableMapOf("USD" to "1.0","SGD" to "Singaporean dollar"),null)
        coEvery { currencyRepository.getLatestRates() } returns flowOf(Resource.Success(latestRatesResponseModel))

        // Act
        val result: Flow<Resource<String>> = convertCurrencyUseCase.run(convertModel)

        // Assert
        assertEquals(
            Resource.Error<String>(
                null,
                ErrorModel("Error occurred during conversion", ErrorModel.ErrorStatus.NOT_DEFINED)
            ).data,
            result.single().data
        )
    }
}