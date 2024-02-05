package com.osamaaftab.bankmisr.common

import java.text.DecimalFormat

object Util {
    fun Double.roundDecimalToOneDigit(): String {
        val decimalFormat = DecimalFormat("#.####")
        return decimalFormat.format(this)
    }
}