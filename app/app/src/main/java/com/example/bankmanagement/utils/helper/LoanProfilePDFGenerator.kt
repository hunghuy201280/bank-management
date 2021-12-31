package com.example.bankmanagement.utils.helper

import android.os.Environment
import com.example.bankmanagement.models.LoanProfile
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import java.io.File
import java.io.FileOutputStream

class LoanProfilePDFGenerator : PDFGenerator<LoanProfile> {
    override fun generatePDF(input: LoanProfile) {
        val pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            .toString()
        val file = File(pdfPath, input.loanApplicationNumber.replace(".","_")+".pdf")

        val outputStream = FileOutputStream(file)
        val writer = PdfWriter(file)
        val pdfDocument = PdfDocument(writer)
        val document = Document(pdfDocument)

        val columnWidth= floatArrayOf(120f,220f,120f,100f)

        val table=Table(2)
        table.addCell(Cell().add(Paragraph("Profile number")))
        table.addCell(Cell().add(Paragraph(input.loanApplicationNumber)))

        table.addCell(Cell().add(Paragraph("Staff name")))
        table.addCell(Cell().add(Paragraph(input.staff.name)))

        table.addCell(Cell().add(Paragraph("Money to loan")))
        table.addCell(Cell().add(Paragraph(input.moneyToLoan.toString())))

        table.addCell(Cell().add(Paragraph("Loan purpose")))
        table.addCell(Cell().add(Paragraph(input.loanPurpose)))

        table.addCell(Cell().add(Paragraph("Loan duration")))
        table.addCell(Cell().add(Paragraph(input.loanDuration.toString())))

        table.addCell(Cell().add(Paragraph("Collateral")))
        table.addCell(Cell().add(Paragraph(input.collateral)))

        table.addCell(Cell().add(Paragraph("Expected source money to repay")))
        table.addCell(Cell().add(Paragraph(input.expectedSourceMoneyToRepay)))

        table.addCell(Cell().add(Paragraph("Benefit from loan")))
        table.addCell(Cell().add(Paragraph(input.benefitFromLoan)))

        table.addCell(Cell().add(Paragraph("Loan type")))
        table.addCell(Cell().add(Paragraph(input.loanType.getName())))

        table.addCell(Cell().add(Paragraph("Loan status")))
        table.addCell(Cell().add(Paragraph(input.loanStatus.getName())))

        table.addCell(Cell().add(Paragraph("Created at")))
        table.addCell(Cell().add(Paragraph(input.getDate().toString())))


        document.add(table)
        document.close()
    }
}