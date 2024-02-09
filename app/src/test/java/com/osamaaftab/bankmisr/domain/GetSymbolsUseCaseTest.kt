package com.osamaaftab.bankmisr.domain

import com.osamaaftab.bankmisr.domain.model.ErrorModel
import com.osamaaftab.bankmisr.domain.model.Resource
import com.osamaaftab.bankmisr.domain.model.SymbolResponseModel
import com.osamaaftab.bankmisr.domain.repository.CurrencyRepository
import com.osamaaftab.bankmisr.domain.usecase.GetSymbolsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetSymbolsUseCaseTest {

    private val currencyRepository: CurrencyRepository = mockk()
    private val getSymbolsUseCase = GetSymbolsUseCase(currencyRepository)

    @Test
    fun `run should return SymbolsModel`() = runBlocking {
        // Arrange
        val symbolResponseModel =
            SymbolResponseModel(true, mutableMapOf(/* mock your symbols data */), null)
        coEvery { currencyRepository.getCurrencySymbols() } returns flowOf(Resource.Success(symbolResponseModel))

        // Act
        val result: Resource<SymbolResponseModel> = getSymbolsUseCase.run().single()

        // Assert
        assertEquals(Resource.Success(symbolResponseModel).data, result.data)
    }

    @Test
    fun `run should return Resource Error`() = runBlocking {
        // Arrange
        val errorModel = ErrorModel("Mock Error", ErrorModel.ErrorStatus.NOT_DEFINED)
        coEvery { currencyRepository.getCurrencySymbols() } returns flowOf(Resource.Error(null, errorModel))

        // Act
        val result: Resource<SymbolResponseModel> = getSymbolsUseCase.run().single()

        // Assert
        assertEquals(Resource.Error(null, errorModel).errorModel, result.errorModel)
    }
}