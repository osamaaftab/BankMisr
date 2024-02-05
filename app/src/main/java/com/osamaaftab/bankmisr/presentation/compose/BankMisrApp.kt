package com.osamaaftab.bankmisr.presentation.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.osamaaftab.bankmisr.presentation.compose.home.CurrencyConverterScreen
import com.osamaaftab.bankmisr.presentation.viewmodel.CurrencyViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun BankMisrApp() {
    val navController = rememberNavController()
    BankMisrNavHost(
        navController = navController
    )
}

@Composable
fun BankMisrNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val currencyViewModel: CurrencyViewModel = koinViewModel()
            CurrencyConverterScreen(currencyViewModel)
        }
    }
}