package com.example.bankmanagement.binding_adapter

import android.icu.text.NumberFormat
import android.net.Uri
import android.os.Build
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.bumptech.glide.Glide
import com.example.bankmanagement.utils.Utils
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit


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
    textView.text = dayOfTheWeek;
}

@BindingAdapter("price")
fun setTextPrice(textView: TextView?, price: Double?) {
    if (textView == null) return
    val priceSpannable: SpannableString =
        getPriceSpannable(price) ?: SpannableString("")
    textView.text = priceSpannable
}

fun getPriceSpannable(price: Double?): SpannableString? {
    var s = "$ "
    if (price != null) {
        s += NumberFormat.getNumberInstance(Locale.US).format(price)
        return SpannableString(s)
    }
    return null
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("bindTime")
fun bindTime(textView: TextView, date: String?) {
    val sdf = SimpleDateFormat("HH:mm");
    val d = Date()

    val dayOfTheWeek: String = sdf.format(date?.let { dateFromISOString(it) } ?: d)
    textView.text = dayOfTheWeek;
}
@BindingAdapter("bindImage")
fun bindImage(imageView: ImageView, uri: String?) {
    uri?.let {
        val circularProgressDrawable = Utils.getCircularLoading(imageView.context);

        Glide
            .with(imageView)
            .load(Uri.parse(uri))
            .centerCrop()
            .placeholder(circularProgressDrawable)
            .into(imageView);

    }

}
@BindingAdapter("bindDateTime")
fun bindDateTime(textView: TextView, dateTime: DateTime?) {
    if(dateTime==null) {
        textView.text = ""
        return
    }
    val dateString="${dateTime.dayOfMonth().asString}/${dateTime.monthOfYear().asString}/${dateTime.year().asString}";

    textView.text=dateString;

}

//region 2way bind double
//-------------------------------------------------------------------------------------------------//

@BindingAdapter("app:text")
fun setDoubleInTextView(tv: TextView, dbl: Double?) {

    try {
        //This will occur when view is first created or when the leading zero is omitted
        if (dbl == null && (tv.text.toString() == "" || tv.text.toString() == ".")) return

        //Check to see what's already there
        val tvDbl = tv.text.toString().toDouble()
        //If it's the same as what we've just entered then return
        // This is when then the double was typed rather than binded
        if (tvDbl == dbl)
            return

        //If it's a new number then set it in the tv
        tv.text = dbl?.toString()

    } catch (nfe: java.lang.NumberFormatException) {

        //This is usually caused when tv.text is blank and we've entered the first digit
        tv.text = dbl?.toString() ?: ""

    }//catch

}//setDoubleInTextView


//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -//


@InverseBindingAdapter(attribute = "app:text")
fun getDoubleFromTextView(editText: TextView): Double? {

    return try {
        editText.text.toString().toDouble()
    } catch (e: NumberFormatException) {
        null
    }//catch

}//getDoubleFromTextView
//endregion

//region 2way bind long
@BindingAdapter("app:text")
fun setLongInTextView(tv: TextView, dbl: Long?) {

    try {
        //This will occur when view is first created or when the leading zero is omitted
        if (dbl == null && (tv.text.toString() == "" || tv.text.toString() == ".")) return

        //Check to see what's already there
        val tvDbl = tv.text.toString().toLong()
        //If it's the same as what we've just entered then return
        // This is when then the double was typed rather than binded
        if (tvDbl == dbl)
            return

        //If it's a new number then set it in the tv
        tv.text = dbl?.toString()

    } catch (nfe: java.lang.NumberFormatException) {

        //This is usually caused when tv.text is blank and we've entered the first digit
        tv.text = dbl?.toString() ?: ""

    }//catch

}//setLongInTextView
@InverseBindingAdapter(attribute = "app:text")
fun getLongFromTextView(editText: TextView): Long? {

    return try {
        editText.text.toString().toLong()
    } catch (e: NumberFormatException) {
        null
    }//catch

}//getLongFromTextView
//endregion




@BindingAdapter("textAttrChanged")
fun setTextChangeListener(editText: TextView, listener: InverseBindingListener) {
    editText.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) = listener.onChange()

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

    })
}


