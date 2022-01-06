package com.example.bankmanagement.utils.helper

import android.content.Context
import java.io.File

interface PDFGenerator<T> {
    fun generatePDF(input:T,context: Context): File
}