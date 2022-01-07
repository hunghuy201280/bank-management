package com.example.bankmanagement.repo

import com.example.bankmanagement.models.*
import com.example.bankmanagement.models.application.BaseApplication
import com.example.bankmanagement.models.application.exemption.ExemptionApplication
import com.example.bankmanagement.models.application.extension.ExtensionApplication
import com.example.bankmanagement.models.application.liquidation.LiquidationApplication
import com.example.bankmanagement.models.customer.Customer
import com.example.bankmanagement.models.customer.CustomerDetail
import com.example.bankmanagement.models.customer.CustomerType
import com.example.bankmanagement.repo.dtos.application.exemption.ExemptionApplicationDto
import com.example.bankmanagement.repo.dtos.application.extension.ExtensionApplicationDto
import com.example.bankmanagement.repo.dtos.application.liquidation.LiquidationApplicationDto
import com.example.bankmanagement.repo.dtos.loan_contract.LoanContractDto
import com.example.bankmanagement.repo.dtos.loan_contract.PaymentReceiptDto
import com.example.bankmanagement.repo.dtos.loan_profiles.CreateLoanProfileData
import com.example.bankmanagement.repo.dtos.loan_profiles.LoanProfileDto
import com.example.bankmanagement.repo.dtos.sign_in.ClockInOutResponse
import com.example.bankmanagement.repo.dtos.up_files.UpFileResp
import org.joda.time.DateTime
import java.io.File


interface MainRepository {
    suspend fun getBranchInfo(
        branchCode: String
    ): BranchInfo

    suspend fun login(
        email: String,
        password: String,
        branchId: String,
    ): Pair<Staff, ClockInOutResponse>

    suspend fun clockIn()
    suspend fun clockOut()

    suspend fun getClockInOutTime(): ClockInOutResponse

    suspend fun getLoanProfiles(
        profileNumber: String? = null,
        customerName: String? = null,
        moneyToLoan: Double? = null,
        loanType: LoanType? = null,
        createdAt: String? = null,
        loanStatus: LoanStatus? = null,
    ): ArrayList<LoanProfile>

    suspend fun searchCustomers(
        name: String? = null,
        phoneNumber: String? = null,
        customerType: CustomerType? = null,
        email: String? = null,
        identityNumber: String? = null,
        isStartWith: Boolean = false,

        ): ArrayList<Customer>


    suspend fun createLoanProfile(
        data: CreateLoanProfileData
    ): LoanProfileDto

    suspend fun upFiles(
        files: List<File>
    ): UpFileResp

    suspend fun updateLoanStatus(
        status: LoanStatus,
        profileId: String,
    )

    suspend fun getContracts(
        customerPhone: String? = null,
        contractNumber: String? = null,
        staffName: String? = null,
        approver: String? = null,
        loanType: LoanType? = null,
        createdAt: String? = null,
        profileNumber: String? = null,
        moneyToLoan: Double? = null,
    ): ArrayList<LoanContract>

    suspend fun hasContract(
        profileId: String
    ): Boolean


    suspend fun createContract(
        profileId: String,
        commitment: String,
        signatureImg: String,
    ): LoanContract


    suspend fun getExemptionApplications(
        limit: Int? = null,
        skip: Int? = null,
        applicationNumber: String? = null,
        contractNumber: String? = null,
        status: LoanStatus? = null,
        createdAt: String? = null,
    ): ArrayList<ExemptionApplication>

    suspend fun getLiquidationApplications(
        limit: Int? = null,
        skip: Int? = null,
        applicationNumber: String? = null,
        contractNumber: String? = null,
        status: LoanStatus? = null,
        createdAt: String? = null,
    ): ArrayList<LiquidationApplication>

    suspend fun getExtensionApplications(
        limit: Int? = null,
        skip: Int? = null,
        applicationNumber: String? = null,
        contractNumber: String? = null,
        status: LoanStatus? = null,
        createdAt: String? = null,
    ): ArrayList<ExtensionApplication>

    suspend fun getApplications(
        limit: Int? = null,
        skip: Int? = null,
        applicationNumber: String? = null,
        contractNumber: String? = null,
        status: LoanStatus? = null,
        createdAt: String? = null,
    ): ArrayList<BaseApplication>

    suspend fun getContract(
        contractId: String? = null,
        contractNumber: String? = null,
    ): LoanContract

    suspend fun getLoanProfile(
        loanProfileId: String? = null
    ): LoanProfile

    suspend fun createDisburseCertificate(
        contractId: String,
        remainingDisburseAmount: Double,
        amount: Double
    )

    suspend fun createLiquidation(
        liquidationApplication: LiquidationApplicationDto
    )

    suspend fun createPayment(
        decisionId: String
    )

    suspend fun createExemption(
        exemptionApplicationDto: ExemptionApplicationDto
    )

    suspend fun createExtension(
        extensionApplicationDto: ExtensionApplicationDto
    )


    suspend fun approveExtension(
        applicationId: String,
        BODSignature: String,
    )


    suspend fun approveExemption(
        applicationId: String,
        BODSignature: String,
    )


    suspend fun approveLiquidation(
        applicationId: String,
        BODSignature: String,
    )

    suspend fun rejectExtension(
        applicationId: String,
    )


    suspend fun rejectExemption(
        applicationId: String,
    )


    suspend fun rejectLiquidation(
        applicationId: String,
    )

    suspend fun addCustomer(
        customerType: CustomerType,
        name: String,
        dateOfBirth: DateTime? = null,
        address: String,
        identityNumber: String,
        identityCardCreatedDate: DateTime,
        phoneNumber: String,
        permanentResidence: String? = null,
        businessRegistrationCertificate: String? = null,
        companyRules: String? = null,
        email: String? = null,

    )


    suspend fun getCustomerDetail(
        customerId: String,
    ): CustomerDetail

    suspend fun updateCustomer(
        id: String,
        name: String?=null,
        address: String?=null,
        identityNumber: String?=null,
        identityCardCreatedDate: String?=null,
        phoneNumber: String?=null,
        email: String?=null,
        permanentResidence: String?=null,
        dateOfBirth: String?=null,
        businessRegistrationCertificate: String?=null,
        companyRules: String?=null,
        customerType: CustomerType,
    )


    fun getToken(): String


}


//abstract class TestAb
//constructor(
//    protected open val test2:String
//
//)
//{
//}
//
//
//
//data class Testaaa(
//    val test1:String, override val test2: String,
//): TestAb(test2) {
//
//}
