package com.example.bankmanagement.binding_adapter

import android.graphics.Paint
import android.icu.text.NumberFormat
import android.net.Uri
import android.os.Build
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.bumptech.glide.Glide
import com.example.bankmanagement.BankApplication
import com.example.bankmanagement.R
import com.example.bankmanagement.models.LoanStatus
import com.example.bankmanagement.models.application.ApplicationType
import com.example.bankmanagement.models.application.BaseApplication
import com.example.bankmanagement.models.application.exemption.ExemptionApplication
import com.example.bankmanagement.models.application.extension.ExtensionApplication
import com.example.bankmanagement.models.application.liquidation.LiquidationApplication
import com.example.bankmanagement.utils.Utils
import com.example.bankmanagement.utils.toLocalDate
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit


//@BindingAdapter("currentRole", "requireRole", requireAll = false)
//fun setVisibilityByRole(view: View, currentRole: String, requireRole: String,) {
//   if(currentRole==requireRole){
//       view.visibility=View.VISIBLE
//   }
//    else{
//       view.visibility=View.GONE
//
//   }
//
//}

@BindingAdapter("layout_width")
fun setLayoutWidth(view: View, width: Float) {
    val layoutParams = view.layoutParams
    layoutParams.width = width.toInt()
    view.layoutParams = layoutParams
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("durationFromTime")
fun setDurationFromDateTime(view: TextView, time: String?) {
    if (time == null) return;
    val date =time.toLocalDate();
    val diff = Date().time - date.time;
    val hms = String.format(
        "%02d:%02d", TimeUnit.MILLISECONDS.toHours(diff),
        TimeUnit.MILLISECONDS.toMinutes(diff) % TimeUnit.HOURS.toMinutes(1),
    )
    view.text = "$hms mins worked so far today";

}

@BindingAdapter("applicationType", "isTitle")
fun setApplicationType(view: TextView, application: BaseApplication, isTitle: Boolean?) {
    view.text = when (application) {
        is ExemptionApplication -> "Exemption"
        is LiquidationApplication -> "Liquidation"
        is ExtensionApplication -> "Extension"
        else -> throw Exception("Unknown class ${application.javaClass}")
    }
    view.text = view.text.toString() + if (isTitle == true) " application detail" else "";
}


@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("bindDate")
fun bindDate(textView: TextView, dateParam: String?) {
    val sdf = SimpleDateFormat("EEEE, dd MMMM");
    val d = Date()

    val dayOfTheWeek: String = sdf.format(dateParam?.toLocalDate() ?: d)
    textView.text = dayOfTheWeek;
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("bindCommonDate")
fun bindCommonDate(textView: TextView, dateParam: String?) {
    val sdf = SimpleDateFormat("dd/MM/yyyy");
    val d = Date()

    val dayOfTheWeek: String = sdf.format(dateParam?.toLocalDate() ?: d)
    textView.text = dayOfTheWeek;
}

@BindingAdapter(requireAll = false, value = ["price", "prefix", "postfix"])
fun setTextPrice(textView: TextView?, price: Double?, prefix: String?, postfix: String?) {
    if (textView == null) return
    val priceSpannable: SpannableString =
        getPriceSpannable(price, prefix, postfix) ?: SpannableString("")

    textView.text = priceSpannable
}

fun getPriceSpannable(price: Double?, prefix: String?, postfix: String?): SpannableString? {
    var s = "$"
    if (price != null) {
        s += NumberFormat.getNumberInstance(Locale.US).format(price)
        prefix?.let { s = prefix + s }
        postfix?.let { s+= postfix}
        return SpannableString(s)
    }
    return null
}

@BindingAdapter("isLinkText")
fun setLinkTextStyle(textView: TextView, isLinkText: Boolean) {
    if (!isLinkText) return
    textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG

    textView.setTextColor(ContextCompat.getColorStateList(textView.context, R.color.blue_link))
}

@BindingAdapter("statusSpan")
fun setStatusSpan(textView: TextView?, status: LoanStatus) {
    when (status) {
        LoanStatus.Pending -> {
            textView?.text = BankApplication.context!!.resources.getString(R.string.pending)
            textView?.setTextColor(
                ContextCompat.getColorStateList(
                    BankApplication.context!!,
                    R.color.textPending
                )
            )
            textView?.background =
                ContextCompat.getDrawable(BankApplication.context!!, R.drawable.bg_status_pending)
        }
        LoanStatus.Done -> {
            textView?.text = BankApplication.context!!.resources.getString(R.string.done)
            textView?.setTextColor(
                ContextCompat.getColorStateList(
                    BankApplication.context!!,
                    R.color.textDone
                )
            )
            textView?.background =
                ContextCompat.getDrawable(BankApplication.context!!, R.drawable.bg_status_done)
        }
        LoanStatus.Rejected -> {
            textView?.text = BankApplication.context!!.resources.getString(R.string.rejected)
            textView?.setTextColor(
                ContextCompat.getColorStateList(
                    BankApplication.context!!,
                    R.color.textReject
                )
            )
            textView?.background =
                ContextCompat.getDrawable(BankApplication.context!!, R.drawable.bg_status_reject)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("bindTime")
fun bindTime(textView: TextView, date: String?) {
    val sdf = SimpleDateFormat("HH:mm");
    val d = Date()

    val dayOfTheWeek: String = sdf.format(date?.toLocalDate() ?: d)
    textView.text = dayOfTheWeek;
}

@BindingAdapter("bindImage")
fun bindImage(imageView: ImageView, uri: String?) {
    uri?.let {
        val circularProgressDrawable = Utils.getCircularLoading(imageView.context);

        Glide
            .with(imageView)
            .load(Uri.parse(uri))
            .placeholder(circularProgressDrawable)
            .into(imageView)

    }

}

@BindingAdapter("bindDateTime")
fun bindDateTime(textView: TextView, dateTime: DateTime?) {
    if (dateTime == null) {
        textView.text = ""
        return
    }
    val dateString =
        "${dateTime.dayOfMonth().asString}/${dateTime.monthOfYear().asString}/${dateTime.year().asString}";

    textView.text = dateString;

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


