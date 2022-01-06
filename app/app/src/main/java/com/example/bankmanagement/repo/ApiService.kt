package com.example.bankmanagement.repo

import com.example.bankmanagement.repo.dtos.admin.RevenueStatisticDto
import com.example.bankmanagement.repo.dtos.application.exemption.ExemptionApplicationDto
import com.example.bankmanagement.repo.dtos.application.extension.ExtensionApplicationDto
import com.example.bankmanagement.repo.dtos.application.liquidation.LiquidationApplicationDto
import com.example.bankmanagement.repo.dtos.branch_info.BranchInfoResponse
import com.example.bankmanagement.repo.dtos.customer.CustomerDetailDto
import com.example.bankmanagement.repo.dtos.customer.GetCustomerResponse
import com.example.bankmanagement.repo.dtos.loan_contract.LoanContractDto
import com.example.bankmanagement.repo.dtos.loan_profiles.CreateLoanProfileData
import com.example.bankmanagement.repo.dtos.loan_profiles.LoanProfileDto
import com.example.bankmanagement.repo.dtos.sign_in.ClockInOutResponse
import com.example.bankmanagement.repo.dtos.sign_in.SignInData
import com.example.bankmanagement.repo.dtos.sign_in.SignInResponse
import com.example.bankmanagement.repo.dtos.up_files.UpFileResp
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {
    @GET("branch_info/{branchCode}")
    suspend fun getBranchInfo(
        @Path("branchCode") branchCode: String
    ): BranchInfoResponse

    @POST("staffs/login")
    suspend fun login(
        @Body body: SignInData
    ): SignInResponse

    @POST("staffs/clock_in")
    suspend fun clockIn(
        @Header("Authorization") token: String
    )

    @POST("staffs/clock_out")
    suspend fun clockOut(
        @Header("Authorization") token: String
    )

    @GET("staffs/clock_in_out_time")
    suspend fun getClockInOutTime(
        @Header("Authorization") token: String
    ): ClockInOutResponse

    @GET("loan_profiles/")
    suspend fun getLoanProfiles(
        @Header("Authorization") token: String,
        @Query("profileNumber") profileNumber: String? = null,
        @Query("customerName") customerName: String? = null,
        @Query("moneyToLoan") moneyToLoan: Double? = null,
        @Query("loanType") loanType: Int? = null,
        @Query("createdAt") createdAt: String? = null,
        @Query("loanStatus") loanStatus: Int? = null,
    ): ArrayList<LoanProfileDto>

    @GET("customers/")
    @JvmSuppressWildcards
    suspend fun searchCustomer(
        @Header(
            "Authorization",
        ) token: String,
        @Query("name") name: String? = null,
        @Query("phoneNumber") phoneNumber: String? = null,
        @Query("customerType") customerType: Int? = null,
        @Query("email") email: String? = null,
        @Query("identityNumber") identityNumber: String? = null,
        @Query("isStartWith") isStartWith: Boolean = false,
    ): GetCustomerResponse

    @POST("loan_profiles/")
    @JvmSuppressWildcards
    suspend fun createLoanProfile(
        @Header(
            "Authorization",
        ) token: String,
        @Body body: CreateLoanProfileData,
    ): LoanProfileDto


    @Multipart
    @POST("images/")
    suspend fun upFiles(
        @Header(
            "Authorization",
        ) token: String,
        @Part images: List<MultipartBody.Part>
    ): UpFileResp

    @Multipart
    @JvmSuppressWildcards
    @POST("/loan_contracts/{id}/send_mail")
    suspend fun sendMail(
        @Header(
            "Authorization",
        ) token: String,
        @Part file: MultipartBody.Part,
        @Path(value = "id", encoded = true) contractId: String,
    )


    @PATCH("loan_profiles/status/{id}")
    @JvmSuppressWildcards
    suspend fun updateLoanStatus(
        @Header(
            "Authorization",
        ) token: String,
        @Body body: Map<String, Any>,
        @Path(value = "id", encoded = true) profileId: String,
    )


    @GET("loan_contracts")
    suspend fun getLoanContracts(
        @Header(
            "Authorization",
        ) token: String,
        @Query("sortBy") sortBy: String = "createdAt:desc",
        @Query("customerPhone") customerPhone: String? = null,
        @Query("contractNumber") contractNumber: String? = null,
        @Query("staffName") staffName: String? = null,
        @Query("approver") approver: String? = null,
        @Query("loanType") loanType: Int? = null,
        @Query("createdAt") createdAt: String? = null,
        @Query("profileNumber") profileNumber: String? = null,
        @Query("moneyToLoan") moneyToLoan: Double? = null,

        ): ArrayList<LoanContractDto>

    @GET("loan_profiles/{loanProfileId}")
    suspend fun getLoanProfile(
        @Header(
            "Authorization",
        ) token: String,
        @Path("loanProfileId") loanProfileId: String
    ): LoanProfileDto

    @GET("loan_profiles/has_contract/{id}")
    suspend fun hasContract(
        @Header(
            "Authorization",
        ) token: String,
        @Path(value = "id", encoded = true) profileId: String,
    ): Boolean


    @POST("loan_contracts")
    @JvmSuppressWildcards
    suspend fun createContract(
        @Header(
            "Authorization",
        ) token: String,
        @Body body: Map<String, Any>,
    ): LoanContractDto


    @GET("exemption_applications")
    suspend fun getExemptionApplications(
        @Header(
            "Authorization",
        ) token: String,
        @Query("limit") limit: Int? = null,
        @Query("skip") skip: Int? = null,
        @Query("applicationNumber") applicationNumber: String? = null,
        @Query("contractNumber") contractNumber: String? = null,
        @Query("status") status: Int? = null,
        @Query("createdAt") createdAt: String? = null,
        @Query("sortBy") sortBy: String = "createdAt:desc",
    ): ArrayList<ExemptionApplicationDto>

    @GET("liquidation_applications")
    suspend fun getLiquidationApplications(
        @Header(
            "Authorization",
        ) token: String,
        @Query("limit") limit: Int? = null,
        @Query("skip") skip: Int? = null,
        @Query("applicationNumber") applicationNumber: String? = null,
        @Query("contractNumber") contractNumber: String? = null,
        @Query("status") status: Int? = null,
        @Query("createdAt") createdAt: String? = null,
        @Query("sortBy") sortBy: String = "createdAt:desc",
    ): ArrayList<LiquidationApplicationDto>

    @GET("extension_applications")
    suspend fun getExtensionApplications(
        @Header(
            "Authorization",
        ) token: String,
        @Query("limit") limit: Int? = null,
        @Query("skip") skip: Int? = null,
        @Query("applicationNumber") applicationNumber: String? = null,
        @Query("contractNumber") contractNumber: String? = null,
        @Query("status") status: Int? = null,
        @Query("status") duration: Long? = null,
        @Query("createdAt") createdAt: String? = null,
        @Query("sortBy") sortBy: String = "createdAt:desc",
    ): ArrayList<ExtensionApplicationDto>


    @GET("loan_contracts/one")
    suspend fun getLoanContract(
        @Header(
            "Authorization",
        ) token: String,
        @Query("_id") contractId: String? = null,
        @Query("contractNumber") contractNumber: String? = null,
    ): LoanContractDto

    @POST("disburse_certificates")
    @JvmSuppressWildcards
    suspend fun createDisburseCertificates(
        @Header(
            "Authorization"
        ) token: String,
        @Body body: Map<String, Any>
    )



    @POST("liquidation_applications")
    @JvmSuppressWildcards
    suspend fun createLiquidationApp(
        @Header(
            "Authorization",
        ) token: String,
        @Body body: Map<String, Any>,
    )

    @POST("exemption_applications")
    @JvmSuppressWildcards
    suspend fun createExemptionApp(
        @Header(
            "Authorization",
        ) token: String,
        @Body body: Map<String, Any>,

        )

    @POST("extension_applications")
    @JvmSuppressWildcards
    suspend fun createExtensionApp(
        @Header(
            "Authorization",
        ) token: String,
        @Body body: Map<String, Any>,
    )

    @POST("liquidation_applications/decision")
    @JvmSuppressWildcards
    suspend fun approveLiquidation(
        @Header(
            "Authorization",
        ) token: String,
        @Body body: Map<String, Any>,
    ): LiquidationApplicationDto


    @POST("exemption_applications/decision")
    @JvmSuppressWildcards
    suspend fun approveExemption(
        @Header(
            "Authorization",
        ) token: String,
        @Body body: Map<String, Any>,
    ): ExemptionApplicationDto


    @POST("extension_applications/decision")
    @JvmSuppressWildcards
    suspend fun approveExtension(
        @Header(
            "Authorization",
        ) token: String,
        @Body body: Map<String, Any>,
    ): ExtensionApplicationDto

    @POST("liquidation_applications/reject")
    @JvmSuppressWildcards
    suspend fun rejectLiquidation(
        @Header(
            "Authorization",
        ) token: String,
        @Body body: Map<String, Any>,
    )


    @POST("exemption_applications/reject")
    @JvmSuppressWildcards
    suspend fun rejectExemption(
        @Header(
            "Authorization",
        ) token: String,
        @Body body: Map<String, Any>,
    )


    @POST("extension_applications/reject")
    @JvmSuppressWildcards
    suspend fun rejectExtension(
        @Header(
            "Authorization",
        ) token: String,
        @Body body: Map<String, Any>,
    )

    @POST("customers")
    @JvmSuppressWildcards
    suspend fun addCustomer(
        @Header(
            "Authorization",
        ) token: String,
        @Body body: Map<String, Any?>,
    )


    @GET("customers/details/{id}")
    @JvmSuppressWildcards
    suspend fun getCustomerDetail(
        @Header(
            "Authorization",
        ) token: String,
        @Path(value = "id", encoded = true) customerId: String,
    ): CustomerDetailDto


    @PATCH("customers/{id}")
    @JvmSuppressWildcards
    suspend fun updateCustomer(
        @Header(
            "Authorization",
        ) token: String,
        @Body body: Map<String, Any?>,
        @Path(value = "id", encoded = true) customerId: String,
        )


    @GET("statistic")
    @JvmSuppressWildcards
    suspend fun getRevenueStatistic(
        @Header(
            "Authorization",
        ) token: String,
        @Query("year") createdAt: Int,

        ): RevenueStatisticDto
}