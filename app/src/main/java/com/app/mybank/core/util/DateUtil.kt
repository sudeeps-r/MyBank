package com.app.mybank.core.util

import org.joda.time.format.DateTimeFormat

interface DateUtil {
    fun format(date: String): String
}

internal class DateUtilImpl : DateUtil {

    override fun format(date: String): String {
        val dtf = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val dt = dtf.parseDateTime(date)
        val ndtfOut = DateTimeFormat.forPattern("dd MMM yyyy");
        return ndtfOut.print(dt)
    }

}