package com.osamaaftab.bankmisr.presentation.compose.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.osamaaftab.bankmisr.domain.model.ConvertModel
import com.osamaaftab.bankmisr.presentation.holder.CurrencyHolder
import com.osamaaftab.bankmisr.presentation.viewmodel.CurrencyViewModel

@Composable
fun CurrencyConverterScreen(
    currencyViewModel: CurrencyViewModel,
) {
    CurrencyConverterScreen(
        symbolList = currencyViewModel.getSymbolDataState.collectAsState().value,
        currencyViewModel
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverterScreen(
    symbolList: MutableMap<String, String>,
    currencyViewModel: CurrencyViewModel
) {

    val context = LocalContext.current

    var amount by remember { mutableStateOf("1.0") }

    var selectedFromCurrency by remember { mutableStateOf(CurrencyHolder()) }

    var selectedToCurrency by remember { mutableStateOf(CurrencyHolder()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Amount input field
        OutlinedTextField(
            value = amount,
            onValueChange = {
                val newValue = if (it.isEmpty() || it.toDouble() < 1.0) "1" else it
                amount = newValue

                validateAndConvert(
                    context,
                    selectedFromCurrency,
                    selectedToCurrency,
                    currencyViewModel,
                    amount
                )
            },
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // From currency dropdown
        CurrencyDropdown(
            currencies = symbolList,
            selectedCurrency = selectedFromCurrency,
            onCurrencySelected = {
                selectedFromCurrency = it
                // Update the converted value whenever the currency changes
                validateAndConvert(
                    context,
                    selectedFromCurrency,
                    selectedToCurrency,
                    currencyViewModel,
                    amount
                )
            },
            label = "From"
        )

        // Swap button
        Button(
            onClick = {
                val tempCurrency = selectedFromCurrency
                selectedFromCurrency = selectedToCurrency
                selectedToCurrency = tempCurrency

                convertCurrency(
                    currencyViewModel,
                    amount.toDouble(),
                    selectedFromCurrency,
                    selectedToCurrency
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Swap Currencies")
        }

        // To currency dropdown
        CurrencyDropdown(
            currencies = symbolList,
            selectedCurrency = selectedToCurrency,
            onCurrencySelected = {
                selectedToCurrency = it
                // Update the converted value whenever the currency changes
                validateAndConvert(
                    context,
                    selectedFromCurrency,
                    selectedToCurrency,
                    currencyViewModel,
                    amount
                )
            },
            label = "To"
        )

        // Converted value display
        OutlinedTextField(
            value = currencyViewModel.getConversionDataState.collectAsState().value,
            onValueChange = {},
            label = { Text("Converted Value") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        if (currencyViewModel.getErrorState.collectAsState().value.first) {
            Text(currencyViewModel.getErrorState.collectAsState().value.second)
        }
    }
}

private fun validateAndConvert(
    context: Context,
    selectedFromCurrency: CurrencyHolder,
    selectedToCurrency: CurrencyHolder,
    currencyViewModel: CurrencyViewModel,
    amount: String
) {
    if (selectedFromCurrency.code.isNotEmpty() && selectedToCurrency.code.isNotEmpty()) {
        if (selectedFromCurrency.code == selectedToCurrency.code) {
            Toast.makeText(context, "To and From currencies are same", Toast.LENGTH_SHORT).show()
        } else {
            convertCurrency(
                currencyViewModel, amount.toDouble(), selectedFromCurrency,
                selectedToCurrency
            )
        }
    }
}

@Composable
fun CurrencyDropdown(
    currencies: Map<String, String>,
    selectedCurrency: CurrencyHolder,
    onCurrencySelected: (CurrencyHolder) -> Unit,
    label: String
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        if (!expanded) {
            Text(label)
        }

        // Dropdown menu
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.small)
                .clickable { expanded = !expanded }
        ) {
            Text(
                text = selectedCurrency.name,
                modifier = Modifier
                    .padding(16.dp)
            )
        }

        if (expanded) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp)
            ) {
                items(currencies.entries.toList()) { (currencyCode, currencyName) ->
                    DropdownMenuItem(onClick = {
                        onCurrencySelected(CurrencyHolder(currencyCode, currencyName))
                        expanded = false
                    },
                        text = {
                            Text(
                                text = currencyName,
                                modifier = Modifier
                                    .padding(8.dp)
                            )
                        })
                    }
                }
            }
        }
    }

// Function to convert currency
fun convertCurrency(
    currencyViewModel: CurrencyViewModel,
    amount: Double,
    fromCurrency: CurrencyHolder,
    toCurrency: CurrencyHolder
) {
    currencyViewModel.convertCurrency(ConvertModel(amount, fromCurrency, toCurrency))
}

