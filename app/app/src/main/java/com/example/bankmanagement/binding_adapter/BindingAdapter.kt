package com.example.bankmanagement.binding_adapter

import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import java.time.Instant
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import androidx.annotation.NonNull
import java.text.SimpleDateFormat
import java.time.temporal.TemporalAccessor


@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("durationFromTime")
fun setDurationFromDateTime(view: TextView,time: String?) {
   if(time==null)return;
    val date = dateFromISOString(time);
    val diff=Date().time-date.time;
    val hms = String.format(
        "%02d:%02d", TimeUnit.MILLISECONDS.toHours(diff),
        TimeUnit.MILLISECONDS.toMinutes(diff) % TimeUnit.HOURS.toMinutes(1),
    )
    view.text="$hms mins worked so far today";

}

@RequiresApi(Build.VERSION_CODES.O)
fun dateFromISOString(isoString:String):Date{
    val timeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME

    val offsetDateTime: OffsetDateTime =
        OffsetDateTime.parse(isoString, timeFormatter)

    val date = Date.from(Instant.from(offsetDateTime))
    return date;
}
@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("bindDate")
fun bindDate(textView: TextView, dateParam: String?) {
    val sdf = SimpleDateFormat("EEEE, dd MMMM");
    val d = Date()

    val dayOfTheWeek: String = sdf.format(dateParam?.let { dateFromISOString(it) } ?: d)
    textView.text=dayOfTheWeek;
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("bindCommonDate")
fun bindCommonDate(textView: TextView, dateParam: String?) {
    val sdf = SimpleDateFormat("dd/MM/yyyy");
    val d = Date()

    val dayOfTheWeek: String = sdf.format(dateParam?.let { dateFromISOString(it) } ?: d)
    textView.text=dayOfTheWeek;
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("bindTime")
fun bindTime(textView: TextView, date: String?) {
    val sdf = SimpleDateFormat("HH:mm");
    val d = Date()

    val dayOfTheWeek: String = sdf.format(date?.let { dateFromISOString(it) } ?: d)
    textView.text=dayOfTheWeek;
}