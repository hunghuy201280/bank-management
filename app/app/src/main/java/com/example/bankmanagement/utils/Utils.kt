package com.example.bankmanagement.utils

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import java.lang.Exception
import android.provider.DocumentsContract

import android.content.ContentUris

import android.os.Environment

import android.os.Build

import android.annotation.SuppressLint
import android.content.CursorLoader
import java.util.*
import kotlin.collections.HashMap



class Utils {

    companion object{

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