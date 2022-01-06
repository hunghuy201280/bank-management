package com.example.bankmanagement.utils.helper


import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import com.example.bankmanagement.R
import com.example.bankmanagement.models.*
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
import com.itextpdf.layout.property.ListNumberingType
import com.itextpdf.layout.property.TextAlignment
import org.apache.commons.io.IOUtils
import org.joda.time.DateTime
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import com.itextpdf.layout.element.AreaBreak


class LoanContractPDFGenerator(val branchInfo: BranchInfo) :
    PDFGenerator<LoanContract> {
    val titleStyle = Style().setTextAlignment(TextAlignment.CENTER)

    private fun getImage(link: String): Image {
        val bitmap = Utils.getBitmapFromURL(link)

        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray: ByteArray = stream.toByteArray()
        bitmap?.recycle()
        val imageData = ImageDataFactory.create(byteArray)
        return Image(imageData).setAutoScale(true)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun generatePDF(input: LoanContract, context: Context) :File{
        val profile=input.loanProfile
        val pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            .toString()
        val file = File(
            pdfPath,
            profile.loanApplicationNumber.replace(".", "_") + DateTime.now().millisOfDay()
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

        generateGeneralInfo(profile, document)

        generateArticle1(profile, document)

        generateProofOfIncome(profile, document)
        generateLoanSecurityMeasures(profile, document)
        generateBenefitFromLoan(profile, document)
        generatePolicies(input, document)





        document.close()
        return file
    }

    private fun generatePolicies(contract: LoanContract, document: Document) {
        Paragraph(
            "Article 4. Terms enforcement:"
        ).apply {
            setBold()
            setFontSize(15f)
            document.add(this)
        }
        List(ListNumberingType.DECIMAL).apply {
            add(ListItem().apply {
                add(Paragraph(
                    "The Parties agree to strictly comply with the agreements in this Contra" +
                            "ct, the Debt Acceptance Agreement and other commitments and agreements" +
                            " to perform the Contract between the Parties (if any) made in writing o" +
                            "r by other method. in accordance with the provisions of this Contract an" +
                            "d the provisions of relevant laws."
                ).setFontSize(13f))
            })
            add(ListItem().apply {
                add(Paragraph(
                    "In case any provision of this Contract is invalidated by a decision of a " +
                            "competent authority, the remaining provisions will remain in effect fo" +
                            "r the Parties. The Parties will discuss and agree to amend and suppleme" +
                            "nt that provision in accordance with the provisions of law."
                ).setFontSize(13f))
            })
            add(ListItem().apply {
                add(Paragraph(
                    "This contract is effective from the date of signing until the Borrower has" +
                            " fulfilled all obligations towards the Bank. The bank keeps 03 copies."
                ).setFontSize(13f))
            })
            add(ListItem().apply {
                add(Paragraph(
                    "The Parties confirm that entering into this Contract is completely volunta" +
                            "ry, not artificial, not forced, deceived, threatened, confused. Each of t" +
                            "he parties has read, understood, agreed and jointly signed below."
                ).setFontSize(13f))
            })

            document.add(this)
        }

        Table(2).apply {
            addCell(Cell().apply {
                add(Paragraph(
                    "BORROWER\n"
                ).apply {
                    addStyle(titleStyle)
                    setBold()
                    setFontSize(16f)
                    add(Text("(Sign and write full name)").apply {
                        setFontSize(13f)
                        addStyle(titleStyle)

                    })
                })
                getImage(contract.loanProfile.getSignatureImage().toString())?.let {
                    this.add(it.setHorizontalAlignment(HorizontalAlignment.CENTER))

                }
            })
            addCell(Cell().apply {
                add(Paragraph(
                    "REPRESENTATIVE OF THE BANK\n"
                ).apply {
                    addStyle(titleStyle)
                    setBold()
                    setFontSize(16f)
                    add(Text("(Signature, full name, stamp)").apply {
                        setFontSize(13f)
                        addStyle(titleStyle)

                    })
                })
                getImage(contract.getSignatureImage().toString()).let {
                    this.add(it.setHorizontalAlignment(HorizontalAlignment.CENTER))

                }
            })

            document.add(this)
        }
    }

    private fun generateBenefitFromLoan(input: LoanProfile, document: Document) {
        Paragraph(
            "Article 3. Benefit from loan:"
        ).apply {
            setBold()
            setFontSize(15f)
            document.add(this)
        }
        List().apply {
            add(ListItem().apply {
                add(Paragraph(
                    "When we give you this amount of money, we will get this benefit: ${input.benefitFromLoan}"
                ).apply {
                    setFontSize(13f)
                })

            })
            document.add(this)
        }

    }

    private fun generateLoanSecurityMeasures(input: LoanProfile, document: Document) {
        Paragraph(
            "Article 3. Loan security measures:"
        ).apply {
            setBold()
            setFontSize(15f)
            document.add(this)
        }
        List(ListNumberingType.DECIMAL).apply {
            add(ListItem().apply {
                add(Paragraph(
                    "To secure the performance of the Borrower's obligations under this Agreem" +
                            "ent, the Parties agree to apply the following security measures:"
                ).apply {
                    setFontSize(13f)
                })
                add(List(ListNumberingType.ENGLISH_LOWER).apply {
                    add(ListItem().add(
                        Paragraph("Mortgage by: ${input.collateral}").setFontSize(13f)
                    ))
                })
                add(List(ListNumberingType.ENGLISH_LOWER).apply {
                    add(ListItem().add(
                        Paragraph("Other security measures and assets as agreed between the " +
                                "Bank and the Borrower and/or the Third Party (if any).").setFontSize(13f)
                    ))
                })
            })
            add(ListItem().apply {
                add(
                    Paragraph(
                        "Details of the security measures and assets are specifically agreed in the " +
                                "following documents signed between the Bank and the Borrower and" +
                                "/or related Parties: (i) Security contract (mortgage) /mortgage" +
                                "/guarantee); (ii) Security Contracts signed after the effective d" +
                                "te of this Loan Contract; and (iii) documents amending, suppleme" +
                                "nting and replacing the above-mentioned Security Contracts."
                    ).setFontSize(13f)
                )
            })
            add(ListItem().apply {
                add(
                    Paragraph(
                        "Expected source money to repay: ${input.expectedSourceMoneyToRepay}"
                    ).setFontSize(13f)
                )
            })
            document.add(this)
        }
    }

    private fun generateProofOfIncome(input: LoanProfile, document: Document) {
        document.add(AreaBreak())
        Paragraph(
            "Article 2. Proof of income:"
        ).apply {
            setBold()
            setFontSize(15f)
            document.add(this)
        }
        val proofTable = Table(2)
        for (i in IncomeType.values().indices) {
            val currentIncome = IncomeType.values()[i]
            val item = input.proofOfIncome.filter { it.imageType == currentIncome }
            if (item.isNotEmpty()) {
                for(proof in item){
                    getImage(
                        proof.getUrl().toString()
                    ).setHorizontalAlignment(HorizontalAlignment.CENTER)?.let {
                        proofTable.addCell(Cell().apply {
                            add(
                                Paragraph(
                                    currentIncome.getName() + "\n"
                                ).apply {
                                    setBold()
                                    setFontSize(15f)
                                }
                            )
                            add(it)
                        })
                    }
                }

            }
//            if (i % 4 == 0 || i % 4 == 1) {
//                proofTable.addCell(Cell().apply {
//                    add(
//                        Paragraph(
//                            currentIncome.getName()
//                        )
//                    )
//                })
//            } else {
//                if (item != null) {
//                    getImage(
//                        item.getUrl().toString()
//                    )?.setHorizontalAlignment(HorizontalAlignment.CENTER)?.let {
//                        proofTable.addCell(Cell().apply {
//                            add(it)
//                        })
//                    }
//                } else {
//                    proofTable.addCell(Cell())
//
//                }
//            }

        }


        document.add(proofTable)
    }

    private fun generateArticle1(input: LoanProfile, document: Document) {
        Paragraph(
            "Article 1. Loan Amount, Loan Term, Loan Purpose, Loan Currency," +
                    " Disbursement Method and Lending Method"
        ).apply {
            setBold()
            setFontSize(15f)
            document.add(this)
        }
        val bigList = List(ListNumberingType.DECIMAL).apply {
            //sub1
            add(ListItem().apply {
                add(Paragraph(
                    "Loan amount: ${input.moneyToLoan.toMoney()} USD"

                ).apply {
                    setFontSize(13f)
                })
            })
            //sub2
            add(ListItem().apply {
                add(Paragraph(
                    "Loan period: ${input.loanDuration} month, " +
                            "from the next day of the day the Bank disburses the first loan to the Borrower.\n"

                ).apply {
                    setFontSize(13f)
                })
            })
            //sub3
            add(ListItem().apply {
                add(Paragraph(
                    "Purpose of using the loan: ${input.loanPurpose}.\n" +
                            "The borrower is responsible for using the loan for the right purpose and commits " +
                            "to take responsibility before the law and the Bank for the purpose of using the loan."

                ).apply {
                    setFontSize(13f)
                })
            })
            //sub4
            add(ListItem().apply {
                add(
                    Paragraph(
                        "Conditions and methods of disbursement:"
                    ).apply {
                        setFontSize(13f)
                    }
                )
                add(
                    List(ListNumberingType.ENGLISH_LOWER).apply {
                        //a)
                        add(ListItem().apply {
                            add(
                                Paragraph(
                                    "The Bank will only disburse to the Borrower after the Borrower and related " +
                                            "parties (if any) have satisfied all conditions and procedures as prescribed by " +
                                            "laws and regulations of the Bank in accordance with the product. loan products" +
                                            " to which the Borrower participates. The Bank Party has the right to refuse to" +
                                            " disburse the disbursement in case the law does not allow the disbursement and/or" +
                                            " there is a force majeure event beyond the control of the Bank Party leading to" +
                                            " the Bank's inability to Disbursement."
                                ).apply {
                                    setFontSize(13f)
                                }
                            )
                        })
                        //b)
                        add(ListItem().apply {
                            add(
                                Paragraph(
                                    "The Bank disburses to the Borrower by method:"
                                ).apply {
                                    setFontSize(13f)
                                    add(Text("In cash").apply {
                                        addStyle(Style().apply {
                                            setBold()
                                        })
                                    })
                                    add(
                                        Paragraph(
                                            "For each loan withdrawal, the Borrower must notify the Bank at least 01 working day " +
                                                    "in advance and enclose it with documents proving the loan use purpose, and at" +
                                                    " the same time sign the Debt Acceptance Agreement and related documents. " +
                                                    "at the request of the Bank. The Borrower can only withdraw loan capital within " +
                                                    "2 months from the date of signing this Contract; After this time limit, " +
                                                    "the Borrower may withdraw capital only with the consent of the Bank. The Borrower" +
                                                    " must withdraw the loan capital for the first time within 12 months from the date " +
                                                    "of signing this Contract, after this time limit, the Borrower can only withdraw " +
                                                    "capital if it is agreed by the Bank. In case the Borrower does not withdraw capital " +
                                                    "within the above time limit or the Borrower withdraws capital after the above time" +
                                                    " limit and is agreed by the Bank, the Borrower must pay the capital withdrawal " +
                                                    "commitment fee as prescribed."

                                        ).apply {
                                            setFontSize(13f)
                                            document.add(this)
                                        }
                                    )
                                }
                            )
                        })
                        //c)
                        add(ListItem().apply {
                            Paragraph(
                                "Loan method: ${input.loanType.getName()}"

                            ).apply {
                                setFontSize(13f)
                            }
                        })

                    }
                )
            })
            //sub5
            add(ListItem().apply {
                add(
                    Paragraph(
                        "Other agreements:"

                    ).apply {
                        setFontSize(13f)
                    })

                add(
                    List(ListNumberingType.ENGLISH_LOWER).apply {
                        //a
                        add(ListItem().apply {
                            add(
                                Paragraph(
                                    "The Borrower by signing this Contract confirms the receipt " +
                                            "of debt for the loan amount as prescribed in Clause 1, " +
                                            "Article 1 of this Contract from the Bank. Accordingly, " +
                                            "this Contract will also be considered as the Debt Acceptance " +
                                            "Agreement signed between the Parties. Accordingly, the terms " +
                                            "\"Debt Acceptance Agreement\" mentioned in this Contract shall" +
                                            " be construed as this Contract."
                                ).apply {
                                    setFontSize(13f)
                                }
                            )
                        })
                        //b
                        add(ListItem().apply {
                            add(
                                Paragraph(
                                    "In case of disbursement by wire transfer, the date the Bank " +
                                            "transfers the loan amount to the Borrower's or other " +
                                            "Beneficiary's account is considered the date the Borrower" +
                                            " receives the loan. The Borrower is responsible for receiving " +
                                            "debt for the entire amount of principal that the Bank has transferred."
                                ).apply {
                                    setFontSize(13f)
                                }
                            )
                        })
                        //c
                        add(ListItem().apply {
                            add(
                                Paragraph(
                                    "In case the Bank disburses to the Borrower in cash through " +
                                            "Vietnam Post Company (VNPOST), the Borrower receives the debt" +
                                            " from the time of disbursement."

                                ).apply {
                                    setFontSize(13f)
                                }
                            )
                        })
                        //d
                        add(ListItem().apply {
                            add(
                                Paragraph(
                                    "The Parties agree that the content at Point b, Clause 4 of " +
                                            "this Article only applies in case the loan is disbursed many times."

                                ).apply {
                                    setFontSize(13f)
                                }
                            )
                        })
                        //e
                        add(ListItem().apply {
                            add(
                                Paragraph(
                                    "In case the Bank has a request to buy insurance for the Collateral, " +
                                            "the Borrower is obliged to carry out the insurance purchase or" +
                                            "ensure the relevant Third Party to buy insurance for the Collateral" +
                                            " in a timely manner. terms and conditions and requirements of the Bank. " +
                                            "In case the Borrower breaches its obligations under this Clause, " +
                                            "the Bank is entitled to apply one, several or concurrently " +
                                            "the following measures:"

                                ).apply {
                                    setFontSize(13f)
                                }

                            )
                            add(List().apply {
                                //sub -1
                                add(ListItem().apply {
                                    add(
                                        Paragraph(
                                            "Increase the lending interest rate on the Borrower's loan;"
                                        ).apply {
                                            setFontSize(13f)
                                        }
                                    )
                                })
                                //sub -2
                                add(ListItem().apply {
                                    add(
                                        Paragraph(
                                            "Penalties for violations against the Borrower " +
                                                    "under the provisions of this Contract;"
                                        ).apply {
                                            setFontSize(13f)
                                        }
                                    )
                                })
                                //sub -3
                                add(ListItem().apply {
                                    add(
                                        Paragraph(
                                            "Stop disbursing, terminating loans and early debt" +
                                                    " collection for all loans under this Contract."
                                        ).apply {
                                            setFontSize(13f)
                                        }
                                    )
                                })
                            })
                        })
                        //f
                        add(ListItem().apply {
                            add(
                                Paragraph(
                                    "The Borrower agrees that, upon the occurrence of the cases" +
                                            " of early debt collection mentioned in Clause 1, Article " +
                                            "6 of this Contract and the cases below, the Borrower agrees" +
                                            " to let the Banker stop disbursement, terminate the loan " +
                                            "and Early recovery of debt to the Borrower:"

                                ).apply {
                                    setFontSize(13f)
                                }

                            )
                            add(List().apply {
                                //sub -1
                                add(ListItem().apply {
                                    add(
                                        Paragraph(
                                            "Borrower or Third Party is an individual (in the " +
                                                    "case of a loan secured by a Third Party's property) " +
                                                    "with cognitive and behavioral difficulties; " +
                                                    "division of common property during the marriage;"
                                        ).apply {
                                            setFontSize(13f)
                                        }
                                    )
                                })
                                //sub -2
                                add(ListItem().apply {
                                    add(
                                        Paragraph(
                                            "The Borrower fails to provide, provides incompletely" +
                                                    " or on time the documents proving or related to " +
                                                    "the use of the loan in accordance with the Bank's " +
                                                    "regulations from time to time; The Borrower does not " +
                                                    "cooperate with the Bank in the process of post-borrowing " +
                                                    "control by the Bank; or the Borrower fails to provide the " +
                                                    "Banker with an income statement during the loan period at " +
                                                    "the request of the Banker;"
                                        ).apply {
                                            setFontSize(13f)
                                        }
                                    )
                                })
                                //sub -3
                                add(ListItem().apply {
                                    add(
                                        Paragraph(
                                            "The Borrower fails to perform or improperly performs " +
                                                    "the requirements and instructions of the Bank Party" +
                                                    " related to the works in order to sign, perform and " +
                                                    "maintain this Contract, the Security Contract such as:" +
                                                    " notarization, authentication. , valuation, registration " +
                                                    "of security transactions, consulting on authentication of" +
                                                    " assets/documents, inventory, management of secured assets," +
                                                    " signing of insurance contracts, renewal/maintenance of " +
                                                    "insurance contracts;"
                                        ).apply {
                                            setFontSize(13f)
                                        }
                                    )
                                })
                                //sub -4
                                add(ListItem().apply {
                                    add(
                                        Paragraph(
                                            "Borrower violates laws and policies of the State; " +
                                                    "there is a decision of a competent State agency that " +
                                                    "the Bank must recover the debt before the due date; " +
                                                    "or the Bank must recover the debt before the due" +
                                                    " date to ensure loan recovery."
                                        ).apply {
                                            setFontSize(13f)
                                        }
                                    )
                                })
                                //sub -5
                                add(ListItem().apply {
                                    add(
                                        Paragraph(
                                            "In addition to the above cases, the Borrower agrees " +
                                                    "that the Banker may refuse to make a loan, refuse" +
                                                    " to disburse to the Borrower without any responsibility" +
                                                    " if it suspects:"
                                        ).apply {
                                            setFontSize(13f)
                                        }
                                    )
                                    add(
                                        List(ListNumberingType.ROMAN_LOWER).apply {
                                            //sub i.
                                            add(ListItem().apply {
                                                add(
                                                    Paragraph(
                                                        "The lending and disbursement may lead to a " +
                                                                "violation of the provisions of the law and/or" +
                                                                " may be directly or indirectly related to the" +
                                                                " violation of the law (including the provisions" +
                                                                " of the Law on Chamber of Commerce and Industry). " +
                                                                ", anti-money laundering, anti-terrorist financing);"
                                                    ).apply {
                                                        setFontSize(13f)
                                                    })
                                            })
                                            //sub ii.
                                            add(ListItem().apply {
                                                add(
                                                    Paragraph(
                                                        "The lending and disbursement related to any " +
                                                                "organization or individual named in the black " +
                                                                "list, warning list, other anti-money laundering " +
                                                                "list issued or recommended by a competent state agency. " +
                                                                "applicable and/or applied at the Bank."
                                                    ).apply {
                                                        setFontSize(13f)
                                                    }
                                                )
                                            })
                                        }
                                    )
                                })
                            })
                        })
                        //g
                        add(ListItem().apply {
                            add(
                                Paragraph(
                                    "The Bank is entitled to:"
                                ).apply {
                                    setFontSize(13f)
                                }

                            )
                            add(List().apply {
                                //sub -1
                                add(ListItem().apply {
                                    add(
                                        Paragraph(
                                            "Directly inspect or request the Borrower to notify" +
                                                    " and provide information about the Borrower's " +
                                                    "compliance with laws including environmental laws;"
                                        ).apply {
                                            setFontSize(13f)
                                        }
                                    )
                                })
                                //sub -2
                                add(ListItem().apply {
                                    add(
                                        Paragraph(
                                            "To use information about the Borrower and its credits" +
                                                    " including information from documents and documents " +
                                                    "provided by the Borrower; contracts and documents signed" +
                                                    " between the Borrower and the Bank to provide related " +
                                                    "partners who cooperate with the Bank to develop, provide" +
                                                    " or relate to the provision of products and services. " +
                                                    "of the Bank Party;"
                                        ).apply {
                                            setFontSize(13f)
                                        }
                                    )
                                })
                                //sub -3
                                add(ListItem().apply {
                                    add(
                                        Paragraph(
                                            "To automatically temporarily lock, deduct and settle " +
                                                    "the Borrower's account (term, no term) at the Bank," +
                                                    " other deposits and assets of the Borrower at the Bank" +
                                                    " or create a collection order request credit institutions " +
                                                    "to open accounts for the Borrower to deduct money to " +
                                                    "transfer to the Bank to recover principals, loan interests," +
                                                    " fees, penalties, payables, refunds and compensations" +
                                                    " to the Bank. under this Contract or any other debt or " +
                                                    "payment obligation of the Borrower to the Bank in accordance" +
                                                    " with the commitments, agreements or other documents " +
                                                    "signed between the Bank and the Borrower."
                                        ).apply {
                                            setFontSize(13f)
                                        }
                                    )
                                })
                            })
                        })
                        //h
                        add(ListItem().apply {
                            add(
                                Paragraph(
                                    "In addition to the notification methods mentioned in Article " +
                                            "13 of this Contract, depending on the Bank's policy from" +
                                            " time to time, the Bank may notify the Borrower through " +
                                            "the method of posting at the head office of the business " +
                                            "location of the Bank. The Bank or the notice on the Bank" +
                                            "'s official website (website: www.vpbank.com.vn). The Bo" +
                                            "rrower is deemed to have received the Bank's notice when" +
                                            " the Bank Party has notified by one/some of the methods " +
                                            "to the Borrower's address, phone number, email, posted at" +
                                            " the local head office. point of sale or announced on th" +
                                            "e website of the Bank. When sending notices to the Borrow" +
                                            "er or having made a notice on the Bank's website, the Ban" +
                                            "k is not obliged to verify that the Borrower has received" +
                                            " the notices sent by the Bank to the Borrower. With respe" +
                                            "ct to the contents that the Bank notifies the Borrower in" +
                                            " writing under the provisions of this Contract, the Debt " +
                                            "Indebtedness Agreement(s), relevant documents or provision" +
                                            "s of law, the Parties agree that, Documents may be prepared" +
                                            " by the Bank and sent in paper form or by data messages via" +
                                            " SMS, email, etc. to the Borrower in accordance with this A" +
                                            "rticle."
                                ).apply {
                                    setFontSize(13f)
                                }

                            )
                        })
                        //i
                        add(ListItem().apply {
                            add(
                                Paragraph(
                                    "By entering into this Agreement, the Borrower acknowledges " +
                                            "and agrees that:"
                                ).apply {
                                    setFontSize(13f)
                                }

                            )
                            add(List().apply {
                                //sub -1
                                add(ListItem().apply {
                                    add(
                                        Paragraph(
                                            "The Borrower has been provided by the Bank with s" +
                                                    "ufficient information related to the loan befor" +
                                                    "e establishing this Contract, including but not" +
                                                    " limited to information such as loan interest r" +
                                                    "ates; principles and factors determining lending" +
                                                    " interest rates in case of applying adjustable l" +
                                                    "ending interest rates; interest rate applied to " +
                                                    "the overdue principal balance; interest rate app" +
                                                    "licable to late payment interest; method of calc" +
                                                    "ulating interest; fee types and rates; informatio" +
                                                    "n about this Agreement;"
                                        ).apply {
                                            setFontSize(13f)
                                        }
                                    )
                                })
                                //sub -2
                                add(ListItem().apply {
                                    add(
                                        Paragraph(
                                            "The Borrower agrees to provide the Bank with all i" +
                                                    "nformation and documents as reasonably required" +
                                                    " by the Bank in order to comply with regulation" +
                                                    "s on Anti-Money Laundering, Combating Terrorist " +
                                                    "Financing, embargo, sanctions. . The Borrower ag" +
                                                    "rees that the Banker may share any information " +
                                                    "concerning the Borrower and/or its stakeholders " +
                                                    "with any law enforcement agency, regulatory auth" +
                                                    "ority or court upon request. of these agencies a" +
                                                    "nd/or as required by law;"
                                        ).apply {
                                            setFontSize(13f)
                                        }
                                    )
                                })
                                //sub -3
                                add(ListItem().apply {
                                    add(
                                        Paragraph(
                                            "The Borrower undertakes that the collateral for " +
                                                    "the loan under this Contract (if any) and all f" +
                                                    "unds are used to fulfill the debt repayment and" +
                                                    " other financial obligations of the Borrower un" +
                                                    "der this Contract, the Debt Receipt Contracts a" +
                                                    "re formed from lawful sources and do not violate" +
                                                    " any provisions of the law (including provisions" +
                                                    " of the law on prevention and combat of money la" +
                                                    "undering and countering terrorist financing);"
                                        ).apply {
                                            setFontSize(13f)
                                        }
                                    )
                                })
                                //sub -4
                                add(ListItem().apply {
                                    add(
                                        Paragraph(
                                            "Any changes to the contents of this Contract will " +
                                                    "take effect only when agreed by the Parties in a" +
                                                    "ccordance with this Contract. Documents and data" +
                                                    " recording amendments and supplements to the Con" +
                                                    "tract in accordance with this Contract are an in" +
                                                    "tegral part of the Contract. Issues not mentioned" +
                                                    " in this Contract will be done in accordance with" +
                                                    " the Debt Acceptance Agreement, other commitments" +
                                                    " and agreements between the Parties (if any) and" +
                                                    " relevant laws."
                                        ).apply {
                                            setFontSize(13f)
                                        }
                                    )
                                })
                            })
                        })
                    }
                )
            })
        }
        document.add(bigList)


    }

    private fun generateGeneralInfo(input: LoanProfile, document: Document) {

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
            businessRegistrationCertImg = getImage(customer.getCert())

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