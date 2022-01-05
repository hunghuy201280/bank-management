package com.example.bankmanagement.utils.helper


import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import com.example.bankmanagement.R
import com.example.bankmanagement.models.BranchInfo
import com.example.bankmanagement.models.LoanProfile
import com.example.bankmanagement.models.customer.BusinessCustomer
import com.example.bankmanagement.models.customer.ResidentCustomer
import com.example.bankmanagement.utils.Utils
import com.example.bankmanagement.utils.toMoney
import com.itextpdf.io.font.FontProgramFactory
import com.itextpdf.io.font.PdfEncodings
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.Style
import com.itextpdf.layout.element.*
import com.itextpdf.layout.element.List
import com.itextpdf.layout.property.HorizontalAlignment
import com.itextpdf.layout.property.TextAlignment
import org.apache.commons.io.IOUtils
import org.joda.time.DateTime
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class LoanProfilePDFGenerator(val branchInfo: BranchInfo, val context: Context) :
    PDFGenerator<LoanProfile> {

    fun getImage(bitmap: Bitmap): Image {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray: ByteArray = stream.toByteArray()
        bitmap.recycle()
        val imageData = ImageDataFactory.create(byteArray)
        return Image(imageData)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun generatePDF(input: LoanProfile) {

        val pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            .toString()
        val file = File(
            pdfPath,
            input.loanApplicationNumber.replace(".", "_") + DateTime.now().millisOfDay()
                .get() + ".pdf"
        )

        val outputStream = FileOutputStream(file)
        val writer = PdfWriter(file)
        val pdfDocument = PdfDocument(writer)
        val document = Document(pdfDocument)
        //#region setup font
        val font = context.resources.openRawResource(R.raw.roboto_regular)
        val fontData: ByteArray = IOUtils.toByteArray(font)
        val fontProgram = FontProgramFactory.createFont(fontData)
        document.setFont(PdfFontFactory.createFont(fontProgram, PdfEncodings.IDENTITY_H, true))

        //#endregion

        generateGeneralInfo(input, document)

        generateArticle(input, document)




        document.close()
    }

    private fun generateArticle(input: LoanProfile, document: Document) {
        Paragraph(
            "Article 1. Loan Amount, Loan Term, Loan Purpose, Loan Currency," +
                    " Disbursement Method and Lending Method"
        ).apply {
            setBold()
            setFontSize(15f)
            document.add(this)
        }
        Paragraph(
            "1. Loan amount: ${input.moneyToLoan.toString().toMoney()} USD\n" +
                    "2. Loan period: ${input.loanDuration} month, " +
                    "from the next day of the day the Bank disburses the first loan to the Borrower.\n" +
                    "3. Purpose of using the loan: ${input.loanPurpose}"

        ).apply {
            setBold()
            setFontSize(13f)
            document.add(this)
        }
    }

    private fun generateGeneralInfo(input: LoanProfile, document: Document) {
        val titleStyle = Style().setTextAlignment(TextAlignment.CENTER)

        val congHoaXaHoi =
            Paragraph("Socialist Republic of Vietnam".uppercase()).addStyle(titleStyle)
                .setFontSize(24f)
        val docLapTuDo =
            Paragraph("Independence – Liberty – Happiness").addStyle(titleStyle).setFontSize(20f)
        val separator1 =
            Paragraph("–––––––––***–––––––––").addStyle(titleStyle).setFontSize(20f)

        val title =
            Paragraph("Loan profile application".uppercase()).addStyle(titleStyle).setFontSize(24f)
                .setBold()

        val loanNumber = Paragraph("Number: ${input.loanApplicationNumber}").apply {
            addStyle(titleStyle)
            setFontSize(20f)
            setUnderline()
        }

        val dateCreated =
            Paragraph("Today, ${Utils.toddMMYYYY(input.createdAt)}, the parties include:").apply {
                addStyle(titleStyle)
                setFontSize(13f)
                setItalic()
            }


        val subtitle =
            Paragraph("Applicable to consumer loans with collateral").addStyle(titleStyle)
                .setFontSize(16f).setItalic()


        val lender =
            Paragraph("* Lender: Incombank - Branch ${branchInfo.branchAddress}").apply {
                setFontSize(13f)
                setBold()
            }


        val lenderInfo = List().apply {
            add(
                ListItem().apply {
                    add(Paragraph("Address: ${branchInfo.branchAddress}").apply {
                        setFontSize(13f)
                    })
                }
            )
            add(
                ListItem().apply {
                    add(Paragraph("Phone number: ${branchInfo.branchPhoneNumber}").apply {
                        setFontSize(13f)
                    })
                }
            )
            add(
                ListItem().apply {
                    add(Paragraph("Fax: ${branchInfo.branchFax}").apply {
                        setFontSize(13f)
                    })
                }
            )
            add(
                ListItem().apply {
                    add(Paragraph(
                        "Authorized representative: Mr/Mrs ${input.staff.name} " +
                                "- Role: ${input.staff.getRoleName()}"
                    ).apply {
                        setFontSize(13f)
                    })
                }
            )
            add(
                ListItem().apply {
                    add(Paragraph("Hereinafter referred to as ").apply {
                        setFontSize(13f)
                        setItalic()
                        add(Text("the Bank Party").setItalic().setBold())
                    })
                }
            )
        }

        val borrower =
            Paragraph("* Borrower: Mr/Mrs ${input.customer.name}").apply {
                setFontSize(13f)
                setBold()
            }

        val customer = input.customer
        val borrowerInfo = List().apply {
            add(
                ListItem().apply {
                    add(Paragraph("Address: ${customer.address}").apply {
                        setFontSize(13f)
                    })
                }
            )
            add(
                ListItem().apply {
                    add(Paragraph(
                        "Identity number: ${customer.identityNumber}" +
                                ", created at: ${Utils.toddMMYYYY(customer.identityCardCreatedDate)}"
                    ).apply {
                        setFontSize(13f)
                    })
                }
            )
            add(
                ListItem().apply {
                    add(Paragraph("Phone number: ${customer.phoneNumber}").apply {
                        setFontSize(13f)
                    })
                }
            )
            add(
                ListItem().apply {
                    add(Paragraph(
                        "Email (if available): ${customer.email ?: ".............."}"
                    ).apply {
                        setFontSize(13f)
                    })
                }
            )
            if (customer is ResidentCustomer) {
                add(
                    ListItem().apply {
                        add(Paragraph(
                            "Permanent residence: ${customer.permanentResidence}"
                        ).apply {
                            setFontSize(13f)
                        })
                    }
                )
                add(
                    ListItem().apply {
                        add(Paragraph(
                            "Date of birth: ${Utils.toddMMYYYY(customer.dateOfBirth)}"
                        ).apply {
                            setFontSize(13f)
                        })
                    }
                )
            } else if (customer is BusinessCustomer) {

                add(
                    ListItem().apply {
                        add(Paragraph(
                            "Company rules: ${customer.companyRules}"
                        ).apply {
                            setFontSize(13f)
                        })
                    }
                )

            }

        }
        var businessRegistrationCertImg: Image? = null
        if (customer is BusinessCustomer) {
            val bitmap = Utils.getBitmapFromURL(customer.getCert())
            bitmap?.let {
                businessRegistrationCertImg = getImage(it)
            }

        }
        val agreement =
            Paragraph("Have agreed and agreed to sign this Loan Profile with the following contents:").apply {
                addStyle(titleStyle)
                setFontSize(13f)
                setItalic()
            }


        document.add(congHoaXaHoi)
        document.add(docLapTuDo)
        document.add(separator1)

        document.add(title)
        document.add(loanNumber)

        document.add(subtitle)
        document.add(dateCreated)
        document.add(lender)
        document.add(lenderInfo)
        document.add(borrower)
        document.add(borrowerInfo)
        businessRegistrationCertImg?.let {
            document.add(Paragraph(
                "- Business registration certificate:"
            ).apply {
                setFontSize(13f)
            })
            document.add(it.setHorizontalAlignment(HorizontalAlignment.CENTER))
        }
        document.add(agreement)
    }
}