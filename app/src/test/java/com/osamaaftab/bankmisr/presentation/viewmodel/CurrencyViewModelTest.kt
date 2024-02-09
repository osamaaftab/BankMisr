package com.osamaaftab.bankmisr.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.osamaaftab.bankmisr.domain.model.ConvertModel
import com.osamaaftab.bankmisr.domain.model.ErrorModel
import com.osamaaftab.bankmisr.domain.model.SymbolResponseModel
import com.osamaaftab.bankmisr.domain.usecase.ConvertCurrencyUseCase
import com.osamaaftab.bankmisr.domain.usecase.GetSymbolsUseCase
import com.osamaaftab.bankmisr.presentation.holder.CurrencyHolder
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.spyk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class CurrencyViewModelTest {

    private val getSymbolsUseCase =  mockk<GetSymbolsUseCase>(relaxed = true)
    private val convertCurrencyUseCase: ConvertCurrencyUseCase = mockk()

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.i(any(), any()) } returns 0
    }

    @Test
    fun `loadCurrencySymbols should update symbolsDataState and onErrorShowState on success`() = runBlocking {
        // Arrange
        val viewModel = spyk(CurrencyViewModel(getSymbolsUseCase, convertCurrencyUseCase))
        val symbolResponseModel = SymbolResponseModel(true, mutableMapOf("USD" to "United States Dollar"),null)

        // Capture the onSuccess lambda argument
        val onSuccessSlot = slot<(SymbolResponseModel) -> Unit>()
       // Capture the onError lambda argument
        val onErrorSlot = slot<(ErrorModel?) -> Unit>()

        // Mock the behavior of getSymbolsUseCase
        coEvery { getSymbolsUseCase.invoke(viewModel.viewModelScope, null, capture(onSuccessSlot), capture(onErrorSlot)) } coAnswers {
            onSuccessSlot.captured.invoke(symbolResponseModel)
        }

        // Act
        viewModel.loadCurrencySymbols()

        // Assert
        assertEquals(symbolResponseModel.symbols, viewModel.getSymbolDataState.value)
        assertEquals(false to "", viewModel.getErrorState.value)
    }

    @Test
    fun `loadCurrencySymbols should update onErrorShowState on failure`() = runBlocking {
        // Arrange
        val viewModel = spyk(CurrencyViewModel(getSymbolsUseCase, convertCurrencyUseCase))
        val errorModel = ErrorModel("Error occurred", ErrorModel.ErrorStatus.NO_CONNECTION)

        // Capture the onSuccess lambda argument
        val onSuccessSlot = slot<(SymbolResponseModel) -> Unit>()
        // Capture the onError lambda argument
        val onErrorSlot = slot<(ErrorModel?) -> Unit>()

        // Mock the behavior of getSymbolsUseCase with specific matchers
        coEvery { getSymbolsUseCase.invoke(viewModel.viewModelScope, null, capture(onSuccessSlot), capture(onErrorSlot)) } coAnswers {
            onErrorSlot.captured.invoke(errorModel)
        }

        // Act
        viewModel.loadCurrencySymbols()

        // Assert
        assertEquals(true to errorModel.errorStatus.name, viewModel.getErrorState.value)
        assertEquals(mutableMapOf<String, String>(), viewModel.getSymbolDataState.value)
    }

    @Test
    fun `convertCurrency should update conversionDataState on success`() = runBlocking {
        // Arrange
        val viewModel = spyk(CurrencyViewModel(getSymbolsUseCase, convertCurrencyUseCase))
        val convertModel = ConvertModel(1.0,CurrencyHolder("USD", "United States Dollar"), CurrencyHolder("EUR", "Euro"))
        val convertedValue = "10.0"

        // Mock the behavior of convertCurrencyUseCase
        coEvery { convertCurrencyUseCase.invoke(viewModel.viewModelScope, any(), any(), any()) } coAnswers {
            val onSuccess = args[2] as (String) -> Unit
            onSuccess(convertedValue)
        }

        // Act
        viewModel.convertCurrency(convertModel)

        // Assert
        assertEquals(convertedValue, viewModel.getConversionDataState.value)
        assertEquals(false to "", viewModel.getErrorState.value)
    }

    @Test
    fun `convertCurrency should update onErrorShowState on failure`() = runBlocking {
        // Arrange

        val viewModel = spyk(CurrencyViewModel(getSymbolsUseCase, convertCurrencyUseCase))
        val convertModel = ConvertModel(1.0,CurrencyHolder("USD", "United States Dollar"), CurrencyHolder("EUR", "Euro"))
        val errorModel = ErrorModel("Error occurred", ErrorModel.ErrorStatus.NO_CONNECTION)

        // Mock the behavior of convertCurrencyUseCase to simulate failure
        coEvery { convertCurrencyUseCase.invoke(viewModel.viewModelScope, any(), any(), any()) } coAnswers {
            val onError = args[3] as (ErrorModel?) -> Unit
            onError(errorModel)
        }

        // Act
        viewModel.convertCurrency(convertModel)

        // Assert
        assertEquals("", viewModel.getConversionDataState.value)
        assertEquals(true to errorModel.errorStatus.name, viewModel.getErrorState.value)
    }
}