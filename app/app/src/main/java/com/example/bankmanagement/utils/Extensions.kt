package com.example.bankmanagement.utils

import android.icu.text.NumberFormat
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.util.*

fun DateTime.toUtcISO():String{
    return toDateTime(DateTimeZone.UTC)!!.toString()
}

fun String.toLocalDateTime():DateTime{
    return DateTime.parse(this).toDateTime(DateTimeZone.getDefault())
}
fun String.toLocalDate(): Date {
    return DateTime.parse(this).toDateTime(DateTimeZone.getDefault()).toDate()
}

fun Double.toMoney():String{
        return NumberFormat.getNumberInstance(Locale.US).format(this)
}
