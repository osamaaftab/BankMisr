package com.osamaaftab.bankmisr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.osamaaftab.bankmisr.presentation.compose.BankMisrApp
import com.osamaaftab.bankmisr.ui.theme.BankmisrTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BankmisrTheme {
                BankMisrApp()
            }
        }
    }
}