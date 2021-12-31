package com.example.bankmanagement.utils.helper

interface PDFGenerator<T> {
    fun generatePDF(input:T)
}