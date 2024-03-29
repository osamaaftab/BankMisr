package com.osamaaftab.bankmisr.presentation.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osamaaftab.bankmisr.domain.model.ConvertModel
import com.osamaaftab.bankmisr.domain.model.ErrorModel
import com.osamaaftab.bankmisr.domain.model.SymbolResponseModel
import com.osamaaftab.bankmisr.domain.usecase.ConvertCurrencyUseCase
import com.osamaaftab.bankmisr.domain.usecase.GetSymbolsUseCase
import com.osamaaftab.bankmisr.presentation.holder.CurrencyHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CurrencyViewModel(
    private val getSymbolsUseCase: GetSymbolsUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase
) : ViewModel() {

    private val onErrorShowState = MutableStateFlow(Pair(false, ""))

    private val symbolsDataState = MutableStateFlow<MutableMap<String, String>>(mutableMapOf())

    private val conversionDataState = MutableStateFlow("")

    private val selectedFromDataState = MutableStateFlow(CurrencyHolder())

    private val selectedToDataState = MutableStateFlow(CurrencyHolder())



    init {
        loadCurrencySymbols()
    }

    private fun getSymbolsSuccess(responseModel: SymbolResponseModel) {
        Log.i(ContentValues.TAG, "result : ${responseModel.symbols}")
        symbolsDataState.value = responseModel.symbols ?: mutableMapOf()
        onErrorShowState.value = Pair(false, "")
    }

    private fun getCurrencyConversionSuccess(value: String) {
        Log.i(ContentValues.TAG, "result : $value")
        conversionDataState.value = value
        onErrorShowState.value = Pair(false, "")
    }

    private fun getCurrencyConversionFails(errorModel: ErrorModel?) {
        Log.i(ContentValues.TAG, "error status: ${errorModel?.errorStatus}")
        Log.i(ContentValues.TAG, "error message: ${errorModel?.message}")
        conversionDataState.value = ""
        onErrorShowState.value = Pair(true, "${errorModel?.errorStatus?.name}")
    }

    private fun getSymbolsFails(errorModel: ErrorModel?) {
        Log.i(ContentValues.TAG, "error status: ${errorModel?.errorStatus}")
        Log.i(ContentValues.TAG, "error message: ${errorModel?.message}")
        conversionDataState.value = ""
        onErrorShowState.value = Pair(true, "${errorModel?.errorStatus?.name}")
    }

    fun loadCurrencySymbols() {
        getSymbolsUseCase.invoke(viewModelScope, null,
            onSuccess = {
                getSymbolsSuccess(it)
            }, onError = {
                getSymbolsFails(it)
            })
    }

    fun convertCurrency(convertModel: ConvertModel) {
        selectedFromDataState.value = convertModel.fromCurrency
        selectedToDataState.value = convertModel.toCurrency
        convertCurrencyUseCase.invoke(viewModelScope, convertModel,
            onSuccess = {
                getCurrencyConversionSuccess(it)
            }, onError = {
                getCurrencyConversionFails(it)
            })
    }

    val getSymbolDataState: StateFlow<MutableMap<String, String>> = symbolsDataState

    val getConversionDataState: StateFlow<String> = conversionDataState

    val getErrorState: StateFlow<Pair<Boolean, String>> = onErrorShowState

    val getSelectedFromDataState : StateFlow<CurrencyHolder> = selectedFromDataState

    val getSelectedToDataState : StateFlow<CurrencyHolder> = selectedToDataState

}