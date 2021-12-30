package com.example.bankmanagement.repo

import com.example.bankmanagement.models.*
import com.example.bankmanagement.models.application.BaseApplication
import com.example.bankmanagement.models.application.exemption.ExemptionApplication
import com.example.bankmanagement.models.application.extension.ExtensionApplication
import com.example.bankmanagement.models.application.liquidation.LiquidationApplication
import com.example.bankmanagement.repo.dtos.application.exemption.ExemptionApplicationDto
import com.example.bankmanagement.repo.dtos.application.exemption.ExemptionApplicationDtoMapper
import com.example.bankmanagement.repo.dtos.application.extension.ExtensionApplicationDto
import com.example.bankmanagement.repo.dtos.application.extension.ExtensionApplicationDtoMapper
import com.example.bankmanagement.repo.dtos.application.liquidation.LiquidationApplicationDto
import com.example.bankmanagement.repo.dtos.application.liquidation.LiquidationApplicationDtoMapper
import com.example.bankmanagement.repo.dtos.branch_info.BranchInfoDtoMapper
import com.example.bankmanagement.repo.dtos.customer.CustomerDtoMapper
import com.example.bankmanagement.repo.dtos.loan_contract.LoanContractDtoMapper
import com.example.bankmanagement.repo.dtos.loan_profiles.CreateLoanProfileData
import com.example.bankmanagement.repo.dtos.loan_profiles.LoanProfileDto
import com.example.bankmanagement.repo.dtos.loan_profiles.LoanProfileDtoMapper
import com.example.bankmanagement.repo.dtos.sign_in.ClockInOutResponse
import com.example.bankmanagement.repo.dtos.sign_in.SignInData
import com.example.bankmanagement.repo.dtos.sign_in.StaffDtoMapper
import com.example.bankmanagement.repo.dtos.up_files.UpFileResp
import com.example.bankmanagement.utils.Utils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class MainRepositoryImpl
constructor(
    private val branchInfoMapper: BranchInfoDtoMapper,
    private val staffDtoMapper: StaffDtoMapper,
    private val profileMapper: LoanProfileDtoMapper,
    private val customerDtoMapper: CustomerDtoMapper,
    private val apiService: ApiService,
    private var accessToken: String = "",
    private val loanContractDtoMapper: LoanContractDtoMapper,
    private val exemptionApplicationDtoMapper: ExemptionApplicationDtoMapper,
    private val liquidationApplicationDtoMapper: LiquidationApplicationDtoMapper,
    private val extensionApplicationDtoMapper: ExtensionApplicationDtoMapper,
) : MainRepository {


    override suspend fun getBranchInfo(branchCode: String): BranchInfo {
        return branchInfoMapper.fromDto(apiService.getBranchInfo(branchCode = branchCode).data);
    }

    override suspend fun login(
        email: String,
        password: String,
        branchId: String
    ): Pair<Staff, ClockInOutResponse> {
        val response = apiService.login(
            body = SignInData(
                email = email,
                password = password,
                branchId = branchId,
            )
        );
        accessToken = response.token;
        return Pair(staffDtoMapper.fromDto(response.staff), response.clockInOut);

    }

    override suspend fun clockIn() {
        apiService.clockIn(accessToken);

        return;
    }

    override suspend fun clockOut() {
        apiService.clockOut(accessToken);

        return;
    }

    override suspend fun getClockInOutTime(): ClockInOutResponse {
        val response = apiService.getClockInOutTime(token = accessToken);
        return response;
    }

    override suspend fun getLoanProfiles(
        profileNumber: String?,
        customerName: String?,
        moneyToLoan: Double?,
        loanType: LoanType?,
        createdAt: String?,
        loanStatus: LoanStatus?
    ): ArrayList<LoanProfile> {
        val response = apiService.getLoanProfiles(
            accessToken,
            profileNumber = profileNumber,
            customerName = customerName,
            moneyToLoan = moneyToLoan,
            loanType = loanType?.value,
            createdAt = createdAt,
            loanStatus = loanStatus?.value,
        );
        return ArrayList(
            response.map
            {
                profileMapper.fromDto(it)
            })
    }

    override suspend fun searchCustomers(query: Map<String, Any>): ArrayList<Customer> {
        val response = apiService.searchCustomer(query = query, token = accessToken);

        return ArrayList(response.data.map { customerDtoMapper.fromDto(it) })

    }

    override suspend fun createLoanProfile(data: CreateLoanProfileData): LoanProfileDto {
        val response = apiService.createLoanProfile(body = data, token = accessToken);
        return response;
    }

    override suspend fun upFiles(files: List<File>): UpFileResp {
        val multipartFiles = arrayListOf<MultipartBody.Part>();

        for (file in files) {
            val fileBody: RequestBody =
                file.asRequestBody(Utils.getMimeType(file.absolutePath)?.toMediaTypeOrNull())
            val part = MultipartBody.Part.createFormData(
                "images",
                file.name,
                fileBody
            );
            multipartFiles.add(part);
        }

        val response = apiService.upFiles(token = accessToken, images = multipartFiles);
        return response;

    }

    override suspend fun updateLoanStatus(status: LoanStatus, profileId: String) {
        val response = apiService.updateLoanStatus(
            token = accessToken,
            profileId = profileId,
            body = mapOf("status" to status.value)
        )
    }

    override suspend fun getContracts(
        customerPhone: String?,
        contractNumber: String?,
        staffName: String?,
        approver: String?,
        loanType: LoanType?,
        createdAt: String?,
        profileNumber: String?,
        moneyToLoan: Double?
    ): ArrayList<LoanContract> {
        val response = apiService.getLoanContracts(
            token = accessToken,
            customerPhone = customerPhone,
            contractNumber = contractNumber,
            staffName = staffName,
            approver = approver,
            loanType = loanType?.value,
            createdAt = createdAt,
            profileNumber = profileNumber,
            moneyToLoan = moneyToLoan,
        )
        val contracts = response.map { loanContractDtoMapper.fromDto(it) }
        return ArrayList(contracts)

    }

    override suspend fun hasContract(profileId: String): Boolean {
        return apiService.hasContract(token = accessToken, profileId)
    }

    override suspend fun createContract(
        profileId: String,
        commitment: String,
        signatureImg: String
    ): LoanContract {
        val response = apiService.createContract(
            token = accessToken, body = mapOf(
                "loanProfile" to profileId,
                "commitment" to commitment,
                "signatureImg" to signatureImg,
            )
        )
        return loanContractDtoMapper.fromDto(response)
    }

    override suspend fun getExemptionApplications(
        limit: Int?,
        skip: Int?,
        applicationNumber: String?,
        contractNumber: String?,
        status: LoanStatus?,
        createdAt: String?
    ): ArrayList<ExemptionApplication> {
        val response = apiService.getExemptionApplications(
            accessToken,
            limit = limit,
            skip = skip,
            applicationNumber = applicationNumber,
            contractNumber = contractNumber,
            status = status?.value,
            createdAt = createdAt,
        )
        return ArrayList(response.map { exemptionApplicationDtoMapper.fromDto(it) })

    }

    override suspend fun getLiquidationApplications(
        limit: Int?,
        skip: Int?,
        applicationNumber: String?,
        contractNumber: String?,
        status: LoanStatus?,
        createdAt: String?
    ): ArrayList<LiquidationApplication> {
        val response = apiService.getLiquidationApplications(
            accessToken,
            limit = limit,
            skip = skip,
            applicationNumber = applicationNumber,
            contractNumber = contractNumber,
            status = status?.value,
            createdAt = createdAt,
        )
        return ArrayList(response.map { liquidationApplicationDtoMapper.fromDto(it) })
    }

    override suspend fun getExtensionApplications(
        limit: Int?,
        skip: Int?,
        applicationNumber: String?,
        contractNumber: String?,
        status: LoanStatus?,
        createdAt: String?
    ): ArrayList<ExtensionApplication> {
        val response = apiService.getExtensionApplications(
            accessToken,
            limit = limit,
            skip = skip,
            applicationNumber = applicationNumber,
            contractNumber = contractNumber,
            status = status?.value,
            createdAt = createdAt,
        )
        return ArrayList(response.map { extensionApplicationDtoMapper.fromDto(it) })
    }

    override suspend fun getApplications(
        limit: Int?,
        skip: Int?,
        applicationNumber: String?,
        contractNumber: String?,
        status: LoanStatus?,
        createdAt: String?
    ): ArrayList<BaseApplication> {
        val exemptions = getExemptionApplications(
            limit = limit,
            skip = skip,
            applicationNumber = applicationNumber,
            contractNumber = contractNumber,
            status = status,
            createdAt = createdAt,
        )
        val liquidations = getLiquidationApplications(
            limit = limit,
            skip = skip,
            applicationNumber = applicationNumber,
            contractNumber = contractNumber,
            status = status,
            createdAt = createdAt,
        )
        val extensions = getExtensionApplications(
            limit = limit,
            skip = skip,
            applicationNumber = applicationNumber,
            contractNumber = contractNumber,
            status = status,
            createdAt = createdAt,
        )
        return ArrayList(exemptions + liquidations + extensions)
    }

    override suspend fun getContract(contractId: String?, contractNumber: String?): LoanContract {
        val response = apiService.getLoanContract(
            token = accessToken,
            contractId = contractId,
            contractNumber = contractNumber
        )
        return loanContractDtoMapper.fromDto(response)
    }

    override suspend fun createLiquidation(liquidationApplication: LiquidationApplicationDto) {
        val body = mapOf(
            "loanContract" to liquidationApplication.loanContract!!,
            "amount" to liquidationApplication.amount!!,
            "signatureImg" to liquidationApplication.signatureImg!!,
            "reason" to liquidationApplication.reason!!,
        )
        apiService.createLiquidationApp(
            token = accessToken,
            body = body
        )
    }

    override suspend fun createExemption(exemptionApplicationDto: ExemptionApplicationDto) {

        val body = mapOf(
            "loanContract" to exemptionApplicationDto.loanContract!!,
            "amount" to exemptionApplicationDto.amount!!,
            "signatureImg" to exemptionApplicationDto.signatureImg!!,
            "reason" to exemptionApplicationDto.reason!!,
        )
        apiService.createExemptionApp(
            token = accessToken,
            body = body
        )
    }

    override suspend fun createExtension(extensionApplicationDto: ExtensionApplicationDto) {
        val body = mapOf(
            "loanContract" to extensionApplicationDto.loanContract!!,
            "amount" to extensionApplicationDto.amount!!,
            "signatureImg" to extensionApplicationDto.signatureImg!!,
            "reason" to extensionApplicationDto.reason!!,
            "duration" to extensionApplicationDto.duration!!,
        )
        apiService.createExtensionApp(
            token = accessToken,
            body = body
        )

    }


    override fun getToken(): String = accessToken;
}

