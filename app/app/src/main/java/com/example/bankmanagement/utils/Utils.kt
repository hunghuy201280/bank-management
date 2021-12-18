package com.example.bankmanagement.utils

import android.app.DatePickerDialog
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Handler
import android.provider.OpenableColumns
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

import android.view.View
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.bankmanagement.R
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.utils.listener.ValueCallBack
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.*
import org.joda.time.DateTime
import retrofit2.HttpException
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*
import kotlin.concurrent.schedule


class Utils {

    companion object {
        fun showDatePicker(v: View,callback: ValueCallBack<DateTime>){
            val c = Calendar.getInstance()
            val initYear = c.get(Calendar.YEAR)
            val initMonth = c.get(Calendar.MONTH)
            val initDay = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(v.context, { _, year, monthOfYear, dayOfMonth ->
                val result= DateTime(year,monthOfYear+1,dayOfMonth,0,0)
                callback.onValue(result)
            }, initYear, initMonth, initDay)

            dpd.show()
        }
        fun showNotifyDialog(
            context: Context,
            mainText: String = "Completed",
            title: String = "Title"
        ) {
            val dialog = MaterialDialog(context).apply {
                title(text = title)
                message(text = mainText)
            }
            Timer().schedule(2000) {
                GlobalScope.launch(Dispatchers.Main) {
                }
                dialog.dismiss()
            }
            dialog.show()


        }

        fun showCompleteDialog(
            context: Context,
            mainText: String = "Completed",
            onDismiss: View.OnClickListener?
        ) {
            val dialog =
                MaterialDialog(context).noAutoDismiss().customView(R.layout.app_complete_dialog)
            dialog.findViewById<TextView>(R.id.completeTextView).text = mainText
            Timer().schedule(2000) {
                GlobalScope.launch(Dispatchers.Main) {
                    onDismiss?.onClick(dialog.view)
                }
                dialog.dismiss()
            }
            dialog.show()

        }

        fun showAlertDialog(
            context: Context,
            title: String = "Warning",
            body: String = "This loan profile will be deleted permanently.\nDo you want to delete?",
            negativeText: String = "Cancel",
            positiveText: String = "OK",
            onPositiveClick: View.OnClickListener? = null,
            onNegativeClick: View.OnClickListener? = null,
        ) {
            val dialog =
                MaterialDialog(context).noAutoDismiss().customView(R.layout.app_alert_dialog)
            dialog.findViewById<TextView>(R.id.title).text = title
            dialog.findViewById<TextView>(R.id.body).text = body
            dialog.findViewById<MaterialButton>(R.id.cancelButton).apply {
                text = negativeText
                setOnClickListener {
                    onNegativeClick?.onClick(this)
                    dialog.dismiss()
                }
            }
            dialog.findViewById<MaterialButton>(R.id.okButton).apply {
                text = positiveText
                setOnClickListener {
                    onPositiveClick?.onClick(this)
                    dialog.dismiss()
                }
            }

            dialog.show()

        }

        suspend fun uploadFile(
            context: Context,
            uris: List<Uri>?,
            mainRepo: MainRepository
        ): List<String> {
            if (uris == null || uris.isEmpty()) return listOf();
            val cacheDir = context.cacheDir;
            val contentResolver = context.contentResolver;

            val files = uris.map {
                val parcelFileDescriptor =
                    contentResolver.openFileDescriptor(it, "r", null) ?: return listOf()

                val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
                val file = File(cacheDir, contentResolver.getFileName(it))
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)
                file
            }
            return try {
                val urls = mainRepo.upFiles(files = files);
                Log.d("Upload file", "File uploaded $urls")
                urls.resultId;

            } catch (e: HttpException) {
                Log.d("Upload file", "Error happened: ${e.response()?.errorBody()?.string()} ")
                listOf()
            }
        }

        fun ContentResolver.getFileName(fileUri: Uri): String {
            var name = ""
            val returnCursor = this.query(fileUri, null, null, null, null)
            if (returnCursor != null) {
                val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                returnCursor.moveToFirst()
                name = returnCursor.getString(nameIndex)
                returnCursor.close()
            }
            return name
        }

        fun getMimeType(url: String?): String? {
            var type: String? = null
            val extension = MimeTypeMap.getFileExtensionFromUrl(url)
            if (extension != null) {
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
            }
            return type
        }

        fun getCircularLoading(context: Context): CircularProgressDrawable {
            val circularProgressDrawable = CircularProgressDrawable(context);
            circularProgressDrawable.apply {
                strokeWidth = 5f
                centerRadius = 30f
                start()
            }
            return circularProgressDrawable;
        }

        fun <T> debounce(
            waitMs: Long = 300L,
            coroutineScope: CoroutineScope,
            destinationFunction: (T) -> Unit
        ): (T) -> Unit {
            var debounceJob: Job? = null
            return { param: T ->
                debounceJob?.cancel()
                debounceJob = coroutineScope.launch {
                    delay(waitMs)
                    destinationFunction(param)
                }
            }
        }
    }

}