package com.app.mybank.core.util

import java.math.RoundingMode
import java.text.DecimalFormat

interface CurrencyUtil {
    fun format(value: Double): String
}

internal class CurrencyUtilImpl : CurrencyUtil {
    override fun format(value: Double): String {
        val df = DecimalFormat("#,###.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(value)
    }

}